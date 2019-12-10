package com.oroarmor.pathfollow;

import static com.oroarmor.eapplet.Drawer.background;

import com.oroarmor.eapplet.Button;
import com.oroarmor.eapplet.Drawer;
import com.oroarmor.eapplet.EApplet;
import com.oroarmor.physics.Vector;

public class PathFollowing extends EApplet {

	Waypoint start;
	Waypoint mid;
	Waypoint end;

	QuinticHermiteSpline aspline;
	QuinticHermiteSpline bspline;

	int steps = 100;

	public static void main(String[] args) {
		EApplet.main(PathFollowing.class.getName(), "Path Following");
	}

	@Override
	public void settings() {
		size(400, 400);
	}

	@Override
	public void setup() {
		end = new Waypoint(new Vector(200, 100), (float) Math.PI / 2, false);
		mid = new Waypoint(new Vector(300, 200), (float) (Math.PI / 2), true);
		start = new Waypoint(new Vector(250, 300), (float) (Math.PI), false);

		aspline = new QuinticHermiteSpline(start, mid);
		bspline = new QuinticHermiteSpline(mid, end);

		Drawer.addDrawable(aspline, bspline);
		Button.addButton(start, mid, end);
	}

	@Override
	public void draw() {
		background(255);
	}

}
