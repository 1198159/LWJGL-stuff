package com.oroarmor.PIDSimulation;

public class PIDCalculator {

	public float kP = 0;
	public float kI = 0;
	public float kD = 0;
	public float kI_DAMP = 0;

	public float errorSum;
	public float pastError;
	public long lastTime;
	public float minOutput = Float.MIN_VALUE;
	public float maxOutput = Float.MAX_VALUE;

	public PIDCalculator(float kP, float kI, float kD, float kI_DAMP) {
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
		this.kI_DAMP = kI_DAMP;
	}

	public PIDCalculator(float kP, float kI, float kD) {
		this(kP, kI, kD, 0.9f);
	}

	public PIDCalculator(float kP) {
		this(kP, 0, 0);
	}

	public void setMinMax(float min, float max) {
		minOutput = min;
		maxOutput = max;
	}

	public float getkP() {
		return kP;
	}

	public void setkP(float kP) {
		this.kP = kP;
	}

	public float getkI() {
		return kI;
	}

	public void setkI(float kI) {
		this.kI = kI;
	}

	public float getkD() {
		return kD;
	}

	public void setkD(float kD) {
		this.kD = kD;
	}

	public float getkI_DAMP() {
		return kI_DAMP;
	}

	public void setkI_DAMP(float kI_DAMP) {
		this.kI_DAMP = kI_DAMP;
	}

	public float getOutput(float error) {

		float outP = error * kP; //really simple, multiply the error by a set value to get outP

		errorSum += error;
		float outI = errorSum * kI;
		errorSum *= kI_DAMP; // multiply errorSum after calculating outI to not sink the value of outI.
								// dampen the value to make sure that error doesnt build up too much over time

		long currentTime = System.currentTimeMillis();
		float timeStep = (float) Math.max((currentTime - lastTime), 1); // doesnt matter that the first step is millis -
																		// 0, because deltaError is divided by such a
																		// large number that it is essentially 0
		float deltaError = (error - pastError) / timeStep;
		float outD = deltaError * kD;

		float output = outP + outI + outD; // sum all the outputs together to get the output value

		output = constrain(output, minOutput, maxOutput); // make sure the output value is within the range of values

		lastTime = currentTime;
		pastError = error; // set values for the next calculation
		
		return output;
	}

	private float constrain(float input, float minValue, float maxValue) {
		return Math.min(Math.max(input, minValue), maxValue);
	}

}
