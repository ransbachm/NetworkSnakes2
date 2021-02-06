package moe.pgnhd.networksnakes2.model;

import java.awt.Point;

public class Food {
	public Point position;
	
	public Food(Point position) {
		this.position = position;
	}
	
	public Food(int x, int y) {
		this(new Point(x,y));
	}
}
