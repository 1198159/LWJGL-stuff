package com.oroarmor.pathfollow;

import static com.oroarmor.eapplet.Drawer.*;

import java.util.ArrayList;

import com.oroarmor.eapplet.Drawable;
import com.oroarmor.physics.Vector;

public class BezierCurve implements Drawable {

	public int priority = Integer.MAX_VALUE - 1;

	public Vector[] points;

	public Vector[] arcPoints;

	Waypoint start;

	ArrayList<Waypoint> extraPoints = new ArrayList<Waypoint>();

	Waypoint end;

	float headingDistance;

	public int steps;

	public BezierCurve(Waypoint start, Waypoint end, float headingDistance, int steps) {
		this.steps = steps;
		this.headingDistance = headingDistance;
		this.start = start;
		this.end = end;

		calculateCurve();
	}

	public void setPoints(float headingDistance) {

//		points.clear();

		points = new Vector[4 + 3 * extraPoints.size()];

		points[0] = (start.pos);
		points[1] = (start.pos.add(new Vector(start.heading).multiply(headingDistance)));

		int i = 2;
		for (Waypoint point : extraPoints) {
			points[i++] = (point.pos.add(new Vector((float) Math.PI + point.heading).multiply(headingDistance)));
			points[i++] = (point.pos);
			points[i++] = (point.pos.add(new Vector(point.heading).multiply(headingDistance)));
		}

		points[points.length - 2] = (end.pos.add(new Vector((float) Math.PI + end.heading).multiply(headingDistance)));
		points[points.length - 1] = (end.pos);
	}

	public void calculateCurve() {
		setPoints(headingDistance);

		arcPoints = new Vector[(int) steps * (extraPoints.size() + 1) + 1];

		// loop through all the points, starting with the first 4 and skipping 3 each
		// loop
		for (int g = 0; g < extraPoints.size() + 1; g++) {

			// do each step
			for (float i = 0; i <= steps; i++) {

				Vector[] neededPoints = new Vector[] { points[g * 3], points[g * 3 + 1], points[g * 3 + 2],
						points[g * 3 + 3] };
				Vector[] nextPoints = neededPoints.clone();

				float t = i / steps;
				for (int j = 3; j >= 0; j--) {

					for (int k = 0; k < j; k++) {
						Vector a = neededPoints[k];
						Vector b = neededPoints[k + 1];
						nextPoints[k] = Vector.lerp(a, b, t);

					}
					neededPoints = nextPoints;
				}
				arcPoints[(int) i + g * steps] = nextPoints[0];
			}
		}
	}

	@Override
	public void draw() {
		stroke(0, 0, 0);
		beginShape();
		for (Vector vec : arcPoints) {
			addVertex(vec.x, vec.y);
		}
		endShape();

		fill(255, 0, 0);
		ellipse(points[1].x, points[1].y, 3, 3);
		line(points[0].x, points[0].y, points[1].x, points[1].y);

		Vector endHeading = points[points.length - 2];
		endHeading = end.pos.add(end.pos.sub(endHeading));
		line(points[points.length - 1].x, points[points.length - 1].y, endHeading.x, endHeading.y);
		ellipse(endHeading.x, endHeading.y, 3, 3);

	}

	@Override
	public int getPriority() {
		return priority;
	}

	@Override
	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void addPoint(Waypoint point) {
		int index = 0;

		float minDist = Vector.dist(start.pos, point.pos);

		for (int i = 0; i < extraPoints.size(); i++) {
			float currentDist = Vector.dist(extraPoints.get(i).pos, point.pos);
			if (currentDist < minDist) {
				minDist = currentDist;
				if (i == 0) {
					index = 0;
				} else if (i == extraPoints.size() - 1) {
					index = extraPoints.size();
				} else {
					if (Vector.dist(extraPoints.get(i).pos, extraPoints.get(i - 1).pos) < Vector
							.dist(extraPoints.get(i).pos, extraPoints.get(i + 1).pos)) {
						index = i - 1;
					} else {
						index = i + 1;
					}
				}
			}
		}

		float endDist = Vector.dist(end.pos, point.pos);
		if (endDist < minDist) {
			minDist = endDist;
			index = extraPoints.size();
		}

		addPoint(point, index);
	}

	public void addPoint(Waypoint point, int index) {
		extraPoints.add(index, point);
	}

}
