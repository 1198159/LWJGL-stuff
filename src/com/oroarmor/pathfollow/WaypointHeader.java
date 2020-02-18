package com.oroarmor.pathfollow;

import static com.oroarmor.eapplet.Drawer.ellipse;
import static com.oroarmor.eapplet.Drawer.fill;
import static com.oroarmor.eapplet.Drawer.line;

import com.oroarmor.eapplet.Button;
import com.oroarmor.eapplet.Color;
import com.oroarmor.eapplet.EApplet;
import com.oroarmor.physics.Vector;

public class WaypointHeader extends Button {

	Vector pos;
	Color fillColor = Color.RED;
	int r = 10;
	boolean mid = false;
	Waypoint point;

	public WaypointHeader(float x, float y, boolean mid, Waypoint point) {
		super(x, y);
		this.pos = new Vector(x, y);
		this.mid = mid;
		this.point = point;
	}

	public WaypointHeader(float x, float y, float heading, boolean mid, Waypoint point) {
		super(x + (float) Math.cos(heading), y + (float) Math.sin(heading));
		this.pos = new Vector(x, y).add(new Vector(heading).multiply(100));
		this.mid = mid;
		this.point = point;
	}

	@Override
	public void draw() {
		fill(fillColor);
		line(pos.x, pos.y, point.pos.x, point.pos.y);
		ellipse(pos.x, pos.y, r, r);

		if (mid) {
			Vector extra = point.pos.add(point.pos.sub(pos));
			line(extra.x, extra.y, point.pos.x, point.pos.y);
			ellipse(extra.x, extra.y, r, r);
		}
	}

	@Override
	public void onHover() {
		if (!getSelected())
			fillColor = new Color(255, 100, 0);
	}

	@Override
	public void onClick() {
		r=13;
		fillColor = new Color(0, 255, 0);
	}

	@Override
	public void onHold() {

		float x = EApplet.mouseX;
		float y = EApplet.mouseY;
		Vector extra = point.pos.add(point.pos.sub(pos));
		float main = (float) Math.sqrt(Math.pow(this.pos.x - x, 2) + Math.pow(this.pos.y - y, 2));
		float other = (float) Math.sqrt(Math.pow(extra.x - x, 2) + Math.pow(extra.y - y, 2));
		if (mid) {
			if (other < main) {
				this.pos = point.pos.add(point.pos.sub(new Vector(x, y)));
			} else {
				this.pos = new Vector(EApplet.mouseX, EApplet.mouseY);
			}
		} else {
			this.pos = new Vector(EApplet.mouseX, EApplet.mouseY);
		}

	}

	@Override
	public void onRelease() {
	}

	@Override
	public boolean inBounds(float x, float y) {
		Vector extra = point.pos.add(point.pos.sub(pos));
		return r > Math.sqrt(Math.pow(this.pos.x - x, 2) + Math.pow(this.pos.y - y, 2))
				|| r > Math.sqrt(Math.pow(extra.x - x, 2) + Math.pow(extra.y - y, 2));
	}

	@Override
	public void onHoverOff() {
		fillColor = Color.RED;
		r=10;
	}

	public Vector getOpposite() {
		return point.pos.add(point.pos.sub(pos));
	}

}
