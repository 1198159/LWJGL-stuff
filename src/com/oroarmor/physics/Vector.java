package com.oroarmor.physics;

import static java.lang.Math.atan2;

public class Vector {

	/**
	 * x value of the Vector
	 */
	public float x;

	/**
	 * y value of the Vector
	 */
	public float y;

	/**
	 * length of the Vector
	 */
	public float length;

	/**
	 * angle of the Vector in radians, (1,0) is 0 radians
	 */
	public float angle;

	/**
	 * Constructs a vector with all values at 0
	 */
	public Vector() {
		this.x = 0;
		this.y = 0;
		this.length = 0;
		this.angle = 0;
	}

	/**
	 * Constructs a vector with length one and a given angle
	 * 
	 * @param angle angle of the vector in radians
	 */
	public Vector(float angle) {
		this.angle = angle;
		this.length = 1;

		this.x = (float) Math.cos(angle);
		this.y = (float) Math.sin(angle);
	}

	/**
	 * Constructs a vector with x and y values
	 * 
	 * @param x x value of the vector
	 * @param y y value of the vector
	 */
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;

		this.length = (float) Math.sqrt(x * x + y * y);
		this.angle = (float) atan2(y, x);
	}

	/**
	 * Adds x and y to the vector, returning a new vector
	 * 
	 * @param x x value to add
	 * @param y y value to add
	 * @return a new Vector added with the x and y values
	 */
	public Vector add(float x, float y) {
		return new Vector(this.x + x, this.y + y);
	}

	/**
	 * Adds two vectors
	 * 
	 * @param other The vector to add
	 * @return The sum of the two vectors
	 */
	public Vector add(Vector other) {
		return add(other.x, other.y);
	}

	/**
	 * Finds the angle between two vectors
	 * 
	 * @param other Vector to find the angle between
	 * @return A radian value for the angle between two vectors
	 */
	public float angleBetween(Vector other) {
		return (float) (atan2(other.y, other.x) - atan2(this.y, this.x));
	}

	/**
	 * Scales a vector through division
	 * 
	 * @param scale The value to divide the vector by
	 * @return The vector after being divided
	 */
	public Vector divide(float scale) {
		this.x /= scale;
		this.y /= scale;
		this.length /= scale;
		return this;
	}

	/**
	 * The dot product of two vectors
	 * 
	 * @param other The vector to calculated the dot product with
	 * @return A float representing the dot product
	 */
	public float dot(Vector other) {
		return this.x * other.x + this.y * other.y;
	}

	/**
	 * Scales a vector through multiplication
	 * 
	 * @param scale The value to multiply the vector by
	 * @return The vector after being multiplied
	 */
	public Vector multiply(float scale) {
		return new Vector(this.x * scale, this.y *scale);
	}

	/**
	 * Subtracts x and y values
	 * 
	 * @param x x value to subtract
	 * @param y y value to subtract
	 * @return a new Vector subtracted with the x and y values
	 */
	public Vector sub(float x, float y) {
		return new Vector(this.x - x, this.y - y);
	}

	/**
	 * Subtracts two vectors
	 * 
	 * @param other The vector to subtract
	 * @return The difference of two vectors
	 */
	public Vector sub(Vector other) {
		return sub(other.x, other.y);
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	public float length() {
		return length;
	}

	public static float dist(Vector vector, Vector vector2) {
		return (float) Math.sqrt(
				(vector.x - vector2.x) * (vector.x - vector2.x) + (vector.y - vector2.y) * (vector.y - vector2.y));
	}

	public Vector setMag(float newMag) {
		return new Vector(x / length, y / length).multiply(newMag);
	}

	public Vector clone() {
		return new Vector(x, y);
	}

	public static Vector lerp(Vector vector, Vector vector2, float t) {
		return new Vector(lerp(vector.x, vector2.x, t), lerp(vector.y, vector2.y, t));
	}

	public static float lerp(float a, float b, float t) {
		return a + (b - a) * t;
	}
}
