package com.oroarmor.raytracing;

import java.util.ArrayList;

import static com.oroarmor.eapplet.Drawer.*;

import com.oroarmor.eapplet.EApplet;
import com.oroarmor.eapplet.Table;
import com.oroarmor.physics.Vector;

public class RaytracingSimulation extends EApplet {

	Boundary[] walls;
	ParticleWithPhysics particle;

	int sceneW = 600;
	int sceneH = 600;

	public boolean reset = false;

	public static void main(String[] args) {
		EApplet.main("com.oroarmor.raytracing.RaytracingSimulation", "Ray Tracing Simulation");
	}

	public void settings() {
		size(1200, 600);
	}

	public void setup() {
		walls = new Boundary[10];
		for (int i = 4; i < walls.length; i++) {
			float x1 = (float) (Math.random() * 600f);
			float x2 = (float) (Math.random() * 600f);
			float y1 = (float) (Math.random() * 600f);
			float y2 = (float) (Math.random() * 600f);
			walls[i] = new Boundary(x1, y1, x2, y2);
		}
		walls[0] = (new Boundary(-1, 0, 601, 0));
		walls[1] = (new Boundary(600, -1, 600, 601));
		walls[2] = (new Boundary(601, 600, -1, 600));
		walls[3] = (new Boundary(0, 601, 0, -1));
		particle = new ParticleWithPhysics(300, 300, 0);
		particle.setRotationDrag(0.8f);
		particle.setVelocityDrag(0.9f);
//		particle.update(300, 300);
	}

	public void draw() {
		super.draw();
		stroke(255, 0, 0);
		for (Boundary wall : walls) {
			wall.show(this);
		}
		particle.show(this);

		particle.look(walls, this);

		if (keyPressed(Key.W)) {
			particle.moveFoward(1);
		}

		if (keyPressed(Key.S)) {
			particle.moveFoward(-1);
		}

		if (keyPressed(Key.A)) {
			particle.rotate(0.5f);
		}

		if (keyPressed(Key.D)) {
			particle.rotate(-0.5f);
		}

		if (keyPressed(Key.UP)) {
			particle.addForce(new Vector(0, 1));
		}
		if (keyPressed(Key.DOWN)) {
			particle.addForce(new Vector(0, -1));
		}
		if (keyPressed(Key.LEFT)) {
			particle.addForce(new Vector(-1, 0));
		}
		if (keyPressed(Key.RIGHT)) {
			particle.addForce(new Vector(1, 0));
		}

		if (keyPressed(Key.C) && keyPressed(Key.R)) {
			if (!reset || keyPressed(Key.SPACE)) {

				reset = true;
				for (int i = 4; i < walls.length; i++) {
					float x1 = (float) (Math.random() * 600f);
					float x2 = (float) (Math.random() * 600f);
					float y1 = (float) (Math.random() * 600f);
					float y2 = (float) (Math.random() * 600f);
					walls[i] = new Boundary(x1, y1, x2, y2);
				}
			}
		} else {
			reset = false;
		}

		particle.rotate(0);
		particle.constrainRotation(-5, 5);
		particle.constrainVelocity(-5, 5);
		particle.update();
		particle.constrainPosition(1, 599, 1, 599);

		fill(0.3f, 0.3f, 0.3f);
		rect(600, 300, 600, 300);
		fill(0.7f, 0.7f, 0.7f);
		rect(600, 300, 600, -300);

		Table<Float, Boundary> map = particle.look(walls, this);
		ArrayList<Float> scene = map.setOne;
		ArrayList<Boundary> bMap = map.setTwo;
		float w = (float) sceneW / scene.size();
		try {
			for (int i = scene.size() - 1; i >= 0; i--) {
				noStroke();
				float sq = scene.get(i) * scene.get(i);
				float wSq = sceneW * sceneW;
				float b = map(sq, 0, wSq, 1, 0);
				float h = map(scene.get(i), 0, (float) (sceneW * Math.sqrt(2)), sceneH, 0);
				fill(b * bMap.get(i).c.getR(), b * bMap.get(i).c.getG(), b * bMap.get(i).c.getB());

				rect(i * w + w / 2f + sceneW, 300, w + 1, h, RectMode.CENTER);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}