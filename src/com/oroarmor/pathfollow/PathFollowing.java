package com.oroarmor.pathfollow;

import static com.oroarmor.eapplet.Drawer.background;

import com.oroarmor.eapplet.Button;
import com.oroarmor.eapplet.Drawer;
import com.oroarmor.eapplet.EApplet;
import com.oroarmor.physics.Vector;

public class PathFollowing extends EApplet {

	Waypoint start;
	Waypoint mid;
	Waypoint mid2;
	Waypoint end;

	QuinticHermiteSpline aspline;
	QuinticHermiteSpline bspline;
	QuinticHermiteSpline cspline;

	int steps = 100;

	public static void main(String[] args) {
		EApplet.main(PathFollowing.class.getName(), "Path Following");
	}

	@Override
	public void settings() {
		size(1000, 1000);
	}

	@Override
	public void setup() {
		end = new Waypoint(new Vector(200, 100), (float) Math.PI / 2, false);
		mid = new Waypoint(new Vector(600, 400), (float) (Math.PI / 2), true);
		mid2 = new Waypoint(new Vector(800, 300), (float) (Math.PI / 2), true);
		start = new Waypoint(new Vector(550, 700), (float) (Math.PI), false);

		aspline = new QuinticHermiteSpline(start, mid, "a");
		bspline = new QuinticHermiteSpline(mid, mid2, "b");
		cspline = new QuinticHermiteSpline(mid2, end, "c");


		Drawer.addDrawable(aspline, bspline, cspline);
		Button.addButton(start, mid, mid2, end);
	}

	@Override
	public void draw() {
		background(255);
	}

}
