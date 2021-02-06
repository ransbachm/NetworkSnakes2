package moe.pgnhd.networksnakes2.controller;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import moe.pgnhd.networksnakes2.model.Food;
import moe.pgnhd.networksnakes2.model.Snake;

public class GameBoard {
	// must stay final, DrawPanel depends
	public final int X_RES = 20;
	public final int Y_RES = 13;
	
	// must be threadsafe
	public List<Snake> snakes = Collections.synchronizedList(new ArrayList<>());
	public List<Food> foods = Collections.synchronizedList(new ArrayList<>());
	
	// number of food tiles which should be spawned at any moment
	private int foodLimit = 3;
	
	public volatile Point lastDirection;
	private UUID playerUUID; // needed for finding the local player
	
	public GameBoard() {
		init();
	}
	
	private void init() {
		// spawn player
		playerUUID = UUID.randomUUID();
		snakes.add(new Snake(3, new Point(4,5), new Point(0,1), playerUUID));
	}
	
	// used for logic updates
	public void tick(ActionEvent ignored) {
		moveSnakes();
	}
	
	private void moveSnakes() {
		for(Snake snake : snakes) {
			// update direction if is player
			if(snake.uuid.equals(playerUUID) && lastDirection != null) {
				snake.direction = lastDirection;
			}
			
			// get old head and direction
			Point oldHead = snake.parts.get(0);
			Point dir = snake.direction;
			
			// create new head and remove oldest part
			Point newHead = new Point(oldHead.x + dir.x, oldHead.y + dir.y);
			snake.parts.add(0, newHead);
			if(snake.parts.size() > snake.length) {
				snake.parts.remove(snake.parts.size()-1);
			}
			
			// border porting logic
			if(newHead.x >= X_RES) newHead.x = 0;
			else if(newHead.x < 0) newHead.x = X_RES;
			else if(newHead.y >= Y_RES) newHead.y = 0;
			else if(newHead.y < 0) newHead.y = Y_RES;
			
			// snake vs. snake collision logic
			// end game if there is any collision
			// also tests for snake self collision
			for(Snake first : snakes) {
				for(Snake other : snakes) {
					if(snakesCollide(first, other) != null) {
						//System.exit(0);
					}
				}
			}
			
			// snake vs. food collision logic
			// remove food if it collides with the player
			for(int i=0; i<foods.size(); i++) {
				Food food = foods.get(i);
				if(food.position.equals(newHead)) {
					foods.remove(i);
					snake.length++;
				}
			}
			spawnFoodIfNeccesary();
			
		}
	}
	
	private void spawnFoodIfNeccesary() {
		// spawn in snake anyway if not possible otherwise
		int tries = 0;
		int limit = 20;
		while(foods.size() < foodLimit) {
			int x = ThreadLocalRandom.current().nextInt(0, X_RES);
			int y = ThreadLocalRandom.current().nextInt(0, Y_RES);
			// do not spawn food where any snake is
			if(SnakePresent(x, y) && tries++ < limit) {
				continue;
			}
			// if there is no snake at that point, spawn the food
			foods.add(new Food(x,y));
		}
	}
	
	// tests if a snake is present at the given coordinates
	private boolean SnakePresent(int x, int y) {
		for(Snake snake : snakes) {
			for(Point part : snake.parts) {
				if(part.x == x && part.y == y) {
					return true;
				}
			}
		}
		return false;
	}
	
	// returns the point of collision or null of there is none
	// 'first' and 'other' could be the same snake
	// in that case self collision is tested
	private Point snakesCollide(Snake first, Snake other) {
		for(int i=0; i<first.parts.size(); i++) {
			for(int j=0; j<other.parts.size(); j++) {
				// things do not collide with themself
				if(i == j && first.equals(other)) continue;
				
				Point firstPart = first.parts.get(i);
				Point otherPart = other.parts.get(j);
				if(firstPart.equals(otherPart)) {
					// it does not matter which point is returned
					return firstPart;
				}
			}
		}
		// no collision
		return null;
	}
	
}
