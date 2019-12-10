package com.oroarmor.pathfollow;

import static com.oroarmor.eapplet.Drawer.ellipse;
import static com.oroarmor.eapplet.Drawer.fill;
import static com.oroarmor.eapplet.Drawer.stroke;

import com.oroarmor.eapplet.Button;
import com.oroarmor.eapplet.Color;
import com.oroarmor.eapplet.EApplet;
import com.oroarmor.physics.Vector;

public class Waypoint extends Button {

	Vector pos;
	WaypointHeader heading;
	int r = 10;
	Color fillColor = new Color(255, 0, 0);

	public Waypoint(Vector pos, float heading, boolean mid) {
		super(pos.x, pos.y);
		this.pos = pos;
		this.heading = new WaypointHeader(pos.x, pos.y, heading, mid, this);
		this.heading.setPriority(getPriority() - 1);
		Button.addButton(this.heading);
	}

	public float getHeading() {
		return (float) (Math.atan2(pos.y - heading.pos.y, pos.x - heading.pos.x));
	}

	@Override
	public void draw() {
		stroke(0, 0, 0);
		fill(fillColor);
		ellipse(pos.x, pos.y, r, r);
	}

	@Override
	public void onHover() {
		if (!getSelected())
			fillColor = new Color(255, 100, 0);
	}

	@Override
	public void onClick() {
		fillColor = new Color(0, 255, 0);
	}

	@Override
	public void onHold() {
		Vector mouseVector = new Vector(EApplet.mouseX, EApplet.mouseY);

		Vector movement = mouseVector.sub(this.pos);

		this.pos = mouseVector;

		this.x = pos.x;
		this.y = pos.y;

		this.heading.pos = this.heading.pos.add(movement);

	}

	@Override
	public void onRelease() {
	}

	@Override
	public boolean inBounds(float x, float y) {
		return r > Math.sqrt(Math.pow(this.pos.x - x, 2) + Math.pow(this.pos.y - y, 2));
	}

	@Override
	public void onHoverOff() {
		fillColor = Color.RED;
	}
}
