package com.oroarmor.raytracing;

import com.oroarmor.eapplet.EApplet;
import static com.oroarmor.eapplet.Drawer.*;
import com.oroarmor.physics.Vector;

public class Ray {

	Vector pos;
	Vector dir;

	public Ray(Vector pos, float angle) {
		this.pos = pos;
		this.dir = new Vector(angle);
	}

	public void setAngle(float angle) {
		this.dir = new Vector(angle);
	}

	public void lookAt(float x, float y) {
		this.dir.x = x - this.pos.x;
		this.dir.y = y - this.pos.y;
		this.dir = this.dir.setMag(1);
	}

	public void show(EApplet applet) {
		stroke(255, 255, 255);
		line(this.pos.x, this.pos.y, this.dir.x * 10, this.dir.y * 10);
	}

	public Vector cast(Boundary wall) {
		float x1 = wall.a.x;
		float y1 = wall.a.y;
		float x2 = wall.b.x;
		float y2 = wall.b.y;

		float x3 = this.pos.x;
		float y3 = this.pos.y;
		float x4 = this.pos.x + this.dir.x;
		float y4 = this.pos.y + this.dir.y;

		float den = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
		if (den == 0) {
			return null;
		}

		float t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / den;
		float u = -((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) / den;
		if (t > 0 && t < 1 && u > 0) {
			Vector pt = new Vector(0, 0);
			pt.x = x1 + t * (x2 - x1);
			pt.y = y1 + t * (y2 - y1);
			return pt;
		} else {
			return null;
		}
	}

}
