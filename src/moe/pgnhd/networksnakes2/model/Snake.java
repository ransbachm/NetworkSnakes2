package moe.pgnhd.networksnakes2.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Snake {
	
	// list of all parts of to the snake
	// pos 0 would be the most recent point and therefore the head
	public List<Point> parts = new ArrayList<>();
	public int length;
	
	// is interpreted "semi-vector" & added to current position for the next position
	public Point direction;
	public UUID uuid;
	
	public Snake(int length, Point startPos, Point direction, UUID uuid) {
		this.length = length;
		this.direction = direction;
		this.parts.add(startPos);
		this.uuid = uuid;
	}

	// snakes equal if their UUIDs match
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		
		if(!(obj instanceof Snake)) 
			return false;
		
		Snake other = (Snake) obj;
		return this.uuid.equals(other.uuid);
	}
	
	
}
