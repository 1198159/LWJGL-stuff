package com.oroarmor.raytracing;

import com.oroarmor.eapplet.EApplet;
import com.oroarmor.physics.Vector;
import static com.oroarmor.eapplet.Drawer.*;

public class Particle {

	public float fov, heading;
	public Vector pos;
	public Ray[] rays;

	public Particle() {
		this.fov = 90;
		this.pos = new Vector(600 / 2, 600 / 2);

		this.heading = 0;
		this.rays = new Ray[(int) this.fov];
		int index = 0;
		for (float a = (int) (-this.fov / 2); a < this.fov / 2; a += 1) {
			this.rays[index] = new Ray(this.pos, (float) a);
			index++;
		}
	}

	public void updateFOV(float fov) {
		this.fov = fov;
		this.rays = new Ray[(int) this.fov];
		int index = 0;
		for (int a = (int) (-this.fov / 2); a < this.fov / 2; a += 1) {
			this.rays[index] = new Ray(this.pos, (float) (a + this.heading));
			index++;
		}
	}

	public void rotate(float angle) {
		this.heading += angle;
		int index = 0;
		for (int a = (int) (-this.fov / 2); a < this.fov / 2; a += 1) {
			this.rays[index].setAngle((float) a + this.heading);
			index++;
		}
	}

	public void move(float amt) {
		Vector vel = new Vector(this.heading);
		vel = vel.setMag(amt);
		this.pos = this.pos.add(vel);
	}

	public void update(float x, float y) {
		this.pos = new Vector(x, y);
	}

	public float[] look(Boundary[] walls, EApplet applet) {
		float[] scene = new float[this.rays.length];
		for (int i = 0; i < this.rays.length; i++) {
			Ray ray = this.rays[i];
			Vector closest = null;
			float record = Integer.MAX_VALUE;
			for (Boundary wall : walls) {
				Vector pt = ray.cast(wall);
				if (pt != null) {
					float d = Vector.dist(this.pos, pt);
					if (d < record) {
						record = d;
						closest = pt;
					}
				}
			}
			if (closest != null) {
				stroke(255, 255, 255, 100);
				line(this.pos.x, this.pos.y, closest.x, closest.y);
			}
			scene[i] = record;
		}
		return scene;
	}

	public void show(EApplet applet) {
		fill(255, 255, 255);
		ellipse(this.pos.x, this.pos.y, 4, 4);
	}

	public void lookAt(float x, float y) {
		float angle = (float) (Math.toDegrees((Math.atan((this.pos.y - y) / (this.pos.x - x))))
				+ ((this.pos.x - x < 0) ? 0 : 180)) - heading;
		this.rotate(angle);
	}
}
