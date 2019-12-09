package com.oroarmor.physics;

public class PhysicsParticle {

	public Vector pos;
	public Vector vel;
	public Vector accel;

	public float heading;
	public float headingVel;
	public float headingDrag;

	public float velDrag;
	
	public boolean bounce = false;
	public float bounceStrength = 0.5f;

	public PhysicsParticle(float x, float y, float heading) {
		this(new Vector(x, y), heading);
	}

	public PhysicsParticle(Vector pos, float heading) {
		this.pos = pos;
		this.vel = new Vector(0, 0);
		this.accel = new Vector(0, 0);
		this.heading = heading;
		this.headingVel = 0;
		this.headingDrag = 1;
		this.velDrag = 1;
	}

	public PhysicsParticle() {
		this(0, 0, 0);
	}

	public void update() {
		this.vel = this.vel.add(this.accel);
		this.accel = new Vector(0, 0);
		this.pos = this.pos.add(this.vel);
		this.vel = this.vel.multiply(this.velDrag);

		this.heading += this.headingVel;
		this.headingVel *= this.headingDrag;
	}

	public void addForce(Vector force) {
		this.accel = this.accel.add(force);
	}

	public void rotate(float rotateForce) {
		this.headingVel += rotateForce;
	}

	public void constrainRotation(float lower, float upper) {
		if (headingVel < lower) {
			headingVel = lower;
		}

		if (headingVel > upper) {
			headingVel = upper;
		}
	}

	public void constrainVelocity(float minSpeed, float maxSpeed) {
		if (this.vel.length() < minSpeed) {
			this.vel = this.vel.setMag(minSpeed);
		}

		if (this.vel.length() > maxSpeed) {
			this.vel = this.vel.setMag(maxSpeed);
		}
	}

	public void constrainPosition(float minX, float maxX, float minY, float maxY) {

		if (this.pos.x > maxX) {
			this.pos.x = maxX;
			if(bounce) {
				this.vel.x *= -bounceStrength;
			}
		}
		if (this.pos.x < minX) {
			this.pos.x = minX;
			if(bounce) {
				this.vel.x *= -bounceStrength;
			}
		}
		if (this.pos.y > maxY) {
			this.pos.y = maxY;
			if(bounce) {
				this.vel.y *= -bounceStrength;
			}
		}
		if (this.pos.y < minY) {
			this.pos.y = minY;
			if(bounce) {
				this.vel.y *= -bounceStrength;
			}
		}
	}

	public void setRotationDrag(float newDragValue) {
		this.headingDrag = newDragValue;
	}

	public void setVelocityDrag(float newDragValue) {
		this.velDrag = newDragValue;
	}

	public void moveFoward(float amt) {
		addForce(new Vector(heading).multiply(amt));
	}
	
	public void setBounce(boolean bounce) {
		this.bounce = bounce;
	}
}
