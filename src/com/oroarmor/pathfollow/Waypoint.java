package com.oroarmor.pathfollow;

import com.oroarmor.eapplet.Drawable;
import com.oroarmor.physics.Vector;

import static com.oroarmor.eapplet.Drawer.*;

public class Waypoint implements Drawable {

	int priority = Integer.MAX_VALUE;
	Vector pos;
	float heading;

	public Waypoint(Vector pos, float heading) {
		this.pos = pos;
		this.heading = heading;
	}

	@Override
	public void draw() {
		fill(255, 0, 0);
		ellipse(pos.x, pos.y, 5, 5);
	}

	@Override
	public int getPriority() {
		return priority;
	}

	@Override
	public void setPriority(int priority) {
		this.priority = priority;
	}
}
