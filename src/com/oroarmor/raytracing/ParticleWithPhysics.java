package com.oroarmor.raytracing;

import com.oroarmor.eapplet.EApplet;
import static com.oroarmor.eapplet.Drawer.*;
import com.oroarmor.eapplet.Table;
import com.oroarmor.physics.PhysicsParticle;
import com.oroarmor.physics.Vector;

public class ParticleWithPhysics extends PhysicsParticle {

	public Ray[] rays;
	public int fov = 90;
	public int numRays = 601;

	public ParticleWithPhysics(float x, float y, float heading) {
		this(new Vector(x, y), heading);
	}

	public ParticleWithPhysics(Vector pos, float heading) {
		super(pos, heading);
		this.rays = new Ray[numRays];
		for (int i = 0; i < numRays; i++) {
			this.rays[i] = new Ray(this.pos, (float) (-this.fov / 2) + ((float) this.fov / (float) numRays) * i);
		}
	}

	public ParticleWithPhysics() {
		this(0, 0, 0);
	}

	public void show(EApplet applet) {
		fill(255, 255, 255);
		ellipse(this.pos.x, this.pos.y, 4, 4);
	}

	public Table<Float, Boundary> look(Boundary[] walls, EApplet applet) {
		float[] scene = new float[this.rays.length];
		Vector[] points = new Vector[this.rays.length];
		Boundary[] closestWalls = new Boundary[this.rays.length];
//		Table<Float, Float> distAndPoints = new Table<Float, Float>();
		Table<Float, Boundary> map = new Table<Float, Boundary>();
		for (int i = 0; i < this.rays.length; i++) {
			Ray ray = this.rays[i];
			Vector closest = null;
			Boundary closestWall = null;
			float record = Integer.MAX_VALUE;
			for (Boundary wall : walls) {
				Vector pt = ray.cast(wall);
				if (pt != null) {
					float d = Vector.dist(this.pos, pt);
					if (d < record) {
						record = d;
						closest = pt;
						closestWall = wall;
					}
				}
			}

			if (closest != null) {
				stroke(255, 255, 255, 100);
				line(this.pos.x, this.pos.y, closest.x, closest.y);
			} else {
				record = 1000;
			}
			scene[i] = (float) (record * Math.abs(Math.cos(ray.dir.angle - Math.toRadians(this.heading))));
			points[i] = closest;
			closestWalls[i] = closestWall;
		}

		// <scene, widthPercents>, closestWalls

		for (int i = 0; i < closestWalls.length - 1; i++) {
			map.add(scene[i], closestWalls[i]);
		}
		return map;
	}

	public void rotate(float angle) {
		super.rotate(angle);
		for (int i = 0; i < numRays; i++) {
			this.rays[i].setAngle(
					(float) (-this.fov / 2) + ((float) i * (float) this.fov / (float) numRays) + this.heading);
		}
	}
}
