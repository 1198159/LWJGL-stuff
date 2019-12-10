package com.oroarmor.pathfollow;

import static com.oroarmor.eapplet.Drawer.addVertex;
import static com.oroarmor.eapplet.Drawer.beginShape;
import static com.oroarmor.eapplet.Drawer.endShape;
import static com.oroarmor.eapplet.Drawer.stroke;

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

	public int steps;

	public BezierCurve(Waypoint start, Waypoint end, int steps) {
		this.steps = steps;
		this.start = start;
		this.end = end;

		calculateCurve();
	}

	public void setPoints() {

//		points.clear();

		points = new Vector[4 + 3 * extraPoints.size()];

		points[0] = (start.pos);
		points[1] = (start.heading.getOpposite());

		int i = 2;
		for (Waypoint point : extraPoints) {
			points[i++] = (point.heading.pos);
			points[i++] = (point.pos);
			points[i++] = (point.heading.getOpposite());
		}

		points[points.length - 2] = (end.heading.pos);
		points[points.length - 1] = (end.pos);
	}

	public void calculateCurve() {
		setPoints();

		arcPoints = new Vector[steps * (extraPoints.size() + 1) + 1];

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
		stroke(0, 255, 0);
		beginShape();
		for (Vector vec : arcPoints) {
			addVertex(vec.x, vec.y);
		}
		endShape();
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
