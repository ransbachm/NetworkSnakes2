package moe.pgnhd.networksnakes2.view;

import java.awt.Point;

public enum KeyBind {
	
	W(new Point(0, -1)),
	A(new Point(-1, 0)),
	S(new Point(0, 1)),
	D(new Point(1, 0));
	
	public final Point direction;
	
	private KeyBind(Point direction) {
		this.direction = direction;
	}
}
