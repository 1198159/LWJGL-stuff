package com.oroarmor.eapplet;

public class Color {
	public static final Color RED = new Color(255, 0, 0);
	float r, g, b, a;

	public Color(float r, float g, float b) {
		this(r, g, b, 0);
	}

	public Color(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public float getR() {
		return r;
	}

	public float getG() {
		return g;
	}

	public float getB() {
		return b;
	}

	public float getA() {
		return a;
	}

	@Override
	public Color clone() {
		return new Color(getR(), getG(), getB(), getA());
	}
}
