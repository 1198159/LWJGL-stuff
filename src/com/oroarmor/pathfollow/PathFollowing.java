package com.oroarmor.pathfollow;

import static com.oroarmor.eapplet.Drawer.background;

import com.oroarmor.eapplet.Drawer;
import com.oroarmor.eapplet.EApplet;
import com.oroarmor.physics.Vector;

public class PathFollowing extends EApplet {

	Waypoint start;
	Waypoint mid;
	Waypoint end;

	BezierCurve curve;

	int steps = 100;

	public static void main(String[] args) {
		EApplet.main(PathFollowing.class.getName(), "Path Following"); //$NON-NLS-1$
	}

	@Override
	public void settings() {
		size(400, 400);
	}

	@Override
	public void setup() {
		start = new Waypoint(new Vector(100, 300), (float) Math.PI / 4);
		mid = new Waypoint(new Vector(200, 200), (float) (5 * Math.PI / 4));
		end = new Waypoint(new Vector(300, 100), (float) (Math.PI / 4));

		System.out.println();
		curve = new BezierCurve(start, end, 100, steps);
		curve.addPoint(mid);

		Drawer.addDrawable(start, mid, end, curve);
	}

	@Override
	public void draw() {
		background(255);

		mid.pos = new Vector((float) Math.cos(frames / 60d) * 30 + 200, (float) Math.sin(frames / 60d) * 30 + 200);
		mid.heading = (float) (5 * Math.PI / 4 + Math.sin(frames / 30d) * Math.PI / 2);

		curve.calculateCurve();
	}

	@Override
	public void mouseClicked() {
		Waypoint newPoint = new Waypoint(new Vector(mouseX, mouseY), random((float) -Math.PI, (float) Math.PI));
		Drawer.addDrawable(newPoint);
		curve.addPoint(newPoint);
	}
}
