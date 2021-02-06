package moe.pgnhd.networksnakes2.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.Timer;

import moe.pgnhd.networksnakes2.controller.GameBoard;
import moe.pgnhd.networksnakes2.model.Food;
import moe.pgnhd.networksnakes2.model.Snake;

public class DrawPanel extends JPanel {
	private static final long serialVersionUID = 3869563495340071034L;
	
	// defines the actual width of the component
	// the window will match this size
	// dynamicly calculated
	private final int xWidth;
	private final int yWidth;
	// the size in pixel of every tile
	private final int tileSize = 25;
	private int frameTime = 1000 / 30;
	private int tickTime = 1000 / 5;
	private Timer graphicsTimer;
	private Timer tickTimer;
	final GameBoard board;
	
	public DrawPanel(GameBoard board) {
		this.board = board;
		xWidth = tileSize * board.X_RES;
		yWidth = tileSize * board.Y_RES;
		init();
	}
	
	private void init() {
		graphicsTimer = new Timer(frameTime, this::update);
		graphicsTimer.start();
		
		tickTimer = new Timer(tickTime, board::tick);
		tickTimer.start();
	}

	
	// render next frame, not used for actual logic
	private void update(ActionEvent ignored) {
		int xTiles = board.X_RES;
		int yTiles = board.Y_RES;
		
		// buffer for faster paint performance and less flicker
		BufferedImage buf = new BufferedImage(xWidth, yWidth, BufferedImage.TYPE_3BYTE_BGR);
		
		// y first, then x
		for(int y=0; y<yTiles; y++) {
			for(int x=0; x<xTiles; x++) {
				Color tileColor = checkColor(x,y);
				paintTile(buf, x, y, tileColor);
			}
		}
		// actually paint buffer on panel
		this.getGraphics().drawImage(buf, 0, 0, null);
		
	}
	
	// returns the color of the given tile, basically a pixel shader
	private Color checkColor(int x, int y) {
		// returns the first present object
		// objects that should be rendered on top of others
		// should be tested first
		Point here = new Point(x,y);
		
		// test if snake part is present
		for(Snake snake : board.snakes) {
			for(Point snakePart : snake.parts) {
				if(snakePart.equals(here)) {
					return Color.RED;
				}
			}
		}
		
		// test if food is present
		for(Food food : board.foods) {
			if(food.position.equals(here)) {
				return Color.GREEN;
			}
		}
		
		// no object found return background color
		return Color.GRAY;
	}
	
	private void paintTile(BufferedImage img, int x, int y, Color tileColor) {
		Graphics g = img.createGraphics();
		g.setColor(tileColor);
		g.fillRect(x*tileSize, y*tileSize, tileSize, tileSize);
	}
	
	// needed for JFrame#pack() to match this component's size
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(xWidth, yWidth);
	}

	// default paint method
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
		
}
