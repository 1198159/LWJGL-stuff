package com.oroarmor.pathfollow;

import static com.oroarmor.eapplet.Drawer.addVertex;
import static com.oroarmor.eapplet.Drawer.beginShape;
import static com.oroarmor.eapplet.Drawer.endShape;
import static com.oroarmor.eapplet.Drawer.stroke;

import com.oroarmor.eapplet.Drawable;
import com.oroarmor.physics.Vector;

public class QuinticHermiteSpline implements Drawable {

	private int priority;

	private Waypoint start, end;
	
	private float ax, bx, cx, dx, ex, fx;
	private float ay, by, cy, dy, ey, fy;

	private float x0, x1, dx0, dx1, ddx0, ddx1;
	private float y0, y1, dy0, dy1, ddy0, ddy1;
	
	private String id;
	public QuinticHermiteSpline(Waypoint start, Waypoint end, String i) {
		id = i;
		this.start = start;
		this.end = end;
		setPositions();
		calculateCoefficients();
	}

	private void setPositions() {
		float scale = 1.2f * Vector.dist(start.pos, end.pos);
		x0 = start.pos.x;
		x1 = end.pos.x;
		dx0 = (float) (Math.cos(start.getHeading()) * scale);
		dx1 = (float) (Math.cos(end.getHeading()) * scale);
		ddx0 = 0;
		ddx1 = 0;

		y0 = start.pos.y;
		y1 = end.pos.y;
		dy0 = (float) (Math.sin(start.getHeading()) * scale);
		dy1 = (float) (Math.sin(end.getHeading()) * scale);
		ddy0 = 0;
		ddy1 = 0;
	}

	private void calculateCoefficients() {
		ax = -6 * x0 - 3 * dx0 - 0.5f * ddx0 + 0.5f * ddx1 - 3 * dx1 + 6 * x1;
		bx = 15 * x0 + 8 * dx0 + 1.5f * ddx0 - ddx1 + 7 * dx1 - 15 * x1;
		cx = -10 * x0 - 6 * dx0 - 1.5f * ddx0 + 0.5f * ddx1 - 4 * dx1 + 10 * x1;
		dx = 0.5f * ddx0;
		ex = dx0;
		fx = x0;

		ay = -6 * y0 - 3 * dy0 - 0.5f * ddy0 + 0.5f * ddy1 - 3 * dy1 + 6 * y1;
		by = 15 * y0 + 8 * dy0 + 1.5f * ddy0 - ddy1 + 7 * dy1 - 15 * y1;
		cy = -10 * y0 - 6 * dy0 - 1.5f * ddy0 + 0.5f * ddy1 - 4 * dy1 + 10 * y1;
		dy = 0.5f * ddy0;
		ey = dy0;
		fy = y0;
	}

	private float getX(float t) {
		return ax * t * t * t * t * t + bx * t * t * t * t + cx * t * t * t + dx * t * t + ex * t + fx;
	}

	private float getY(float t) {
		return ay * t * t * t * t * t + by * t * t * t * t + cy * t * t * t + dy * t * t + ey * t + fy;
	}
	public boolean tt = true;
	@Override
	public void draw() {
		setPositions();
		calculateCoefficients();
		stroke(0, 0, 0);
		beginShape();
		float ot;
		double angle;
		int min = 0;
		int max = 100;
		for (int i = min; i <= max; i++) {
			float t = i * 1f / 100f;
			ot = Math.max((i-1) * 1f / 100f, min);
			//nt = Math.min(i+1 * 1f / 10f, max);
			addVertex(getX(t), getY(t));
			if(i != 0) {
				angle = Math.atan((getY(t)-getY(ot))/(getX(t)-getX(ot)));
			}else {
				angle = this.start.getHeading();
			}
			if(tt)
			System.out.println(id+new Position(getX(t), getY(t), angle));
			
		}
		tt=false;
		endShape();
	}

	@Override
	public int getPriority() {
		return priority;
	}

	@Override
	public void setPriority(int priority) {
		this.priority = Integer.MAX_VALUE - 1;
	}

}
