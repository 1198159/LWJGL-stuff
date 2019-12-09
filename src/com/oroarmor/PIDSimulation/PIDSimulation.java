package com.oroarmor.PIDSimulation;

import com.oroarmor.eapplet.EApplet;
import static com.oroarmor.eapplet.Drawer.*;

import com.oroarmor.physics.PhysicsParticle;
import com.oroarmor.physics.Vector;

public class PIDSimulation extends EApplet {

	PhysicsParticle PIDParticle;

	public static final float VEL_kP = 0.075f;
	public static final float VEL_kI = 0.00025f;
	public static final float VEL_kD = 0.01f;
	public static final float VEL_kI_DAMP = 0.97f;

	public PIDCalculator velocityCalculator = new PIDCalculator(VEL_kP, VEL_kI, VEL_kD, VEL_kI_DAMP);

	public static final float ACCEL_kP = 1f;
	public static final float ACCEL_kI = 0.001f;
	public static final float ACCEL_kD = 0.01f;
	public static final float ACCEL_kI_DAMP = 0.9f;

	public PIDCalculator accelerationCalculator = new PIDCalculator(ACCEL_kP, ACCEL_kI, ACCEL_kD, ACCEL_kI_DAMP);

	public Vector setPos = new Vector(200, 200); // 0 to width

	public static void main(String[] args) {
		EApplet.main("com.oroarmor.PIDSimulation.PIDSimulation", "PID Simulation");
	}

	@Override
	public void setup() {
		PIDParticle = new PhysicsParticle(width / 2, height / 2, 0);
		PIDParticle.setBounce(false);
	}

	int r = 15;

	@Override
	public void draw() {
		background(255);
		stroke(0, 0, 0);

		fill(255, 0, 0);
		rect(setPos.x, setPos.y, r, r, RectMode.CENTER);

		noFill();
		ellipse(PIDParticle.pos.x, PIDParticle.pos.y, r, r);

		float velocityOutput = velocityCalculator.getOutput((Vector.dist(setPos, PIDParticle.pos)));

		float angleToSet = (float) Math.atan2(//
				setPos.y - PIDParticle.pos.y, //
				setPos.x - PIDParticle.pos.x);

		Vector velocityVector = new Vector(angleToSet).multiply(velocityOutput);

		float accelerationOutput = accelerationCalculator.getOutput((Vector.dist(velocityVector, PIDParticle.vel)));

		float angleBetweenVelocity = (float) Math.atan2(//
				velocityVector.y - PIDParticle.vel.y, //
				velocityVector.x - PIDParticle.vel.x);

		Vector accelerationVector = new Vector(angleBetweenVelocity).multiply(accelerationOutput);

		PIDParticle.addForce(accelerationVector);

		PIDParticle.constrainVelocity(-5, 5);

		PIDParticle.update();

		if (mouseIsPressed && mouseButton == MouseButtons.RIGHT) {
			hideCursor();
			setPos = new Vector(mouseX, mouseY);
		}
	}

	@Override
	public void settings() {
		size(400, 400);
	}

	public void mouseClicked() {
		setPos = new Vector(mouseX, mouseY);
	}

	public void mouseReleased() {
		showCursor();
	}

	public void keyPressed() {
		if (keys[Key.SPACE]) {
			setPos = new Vector(random(width), random(width));
		}
	}
}
