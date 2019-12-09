package com.oroarmor.eapplet;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;

import java.util.ArrayList;
import java.util.Iterator;

import org.lwjgl.opengl.GL11;

public class Drawer {

	public static enum ColorMode {
		Percent, Value
	}

	public static enum DrawMode {
		Center, Corner;
	}

	public static enum RectMode {
		CENTER, CORNER
	}

	static ColorMode colorMode = ColorMode.Value;
	static DrawMode drawMode = DrawMode.Corner;

	static Color fill = new Color(0, 0, 0);
	static Color stroke = new Color(0, 0, 0);

	public static int width;
	public static int height;

	public static ArrayList<Drawable> objects = new ArrayList<Drawable>(1);

	public static void addDrawable(Drawable... ds) {
		for (Drawable d : ds) {
			objects.add(d);
		}
		objects.sort(new Drawable.DrawableComparator());
	}

	public static void drawObjects() {
		Iterator<Drawable> iter = objects.iterator();

		while (iter.hasNext()) {
			iter.next().draw();
		}
	}

	public static void addVertex(float x, float y) {
		x = map(x, 0, width, -1, 1);
		y = map(y, 0, height, -1, 1);
		glVertex2d(x, y);
	}

	public static void background(int g) {
		Color fillColor;
		if (fill == null) {
			fillColor = null;
		} else {
			fillColor = fill.clone();
		}
		fill(new Color(g, g, g));

		rect(0, height, width, height);

		fill = fillColor;
	}

	public static void beginShape() {
		glBegin(GL11.GL_LINE_STRIP);
		setStroke();
	}

	public static void ellipse(float x, float y, float r, float r2) {

		if (drawMode == DrawMode.Corner) {
			setFill();
			glBegin(GL11.GL_POLYGON);
			for (float i = 0; i < 3600; i++) {
				glVertex2d(map((float) Math.cos(i / 10f) * r + x, 0, width, -1, 1),
						map((float) Math.sin(i / 10f) * r2 + y, 0, height, -1, 1));
			}
			glEnd();

			setStroke();
			glBegin(GL11.GL_LINE_LOOP);
			for (float i = 0; i < 3600; i++) {
				glVertex2d(map((float) Math.cos(Math.toRadians(i / 10f)) * r + x, 0, width, -1, 1),
						map((float) Math.sin(Math.toRadians(i / 10f)) * r2 + y, 0, height, -1, 1));
			}
			glEnd();

		}
	}

	public static void endShape() {
		glEnd();
	}

	public static void fill(Color c) {
		fill = c.clone();
	}

	public static void fill(float r, float g, float b) {
		fill = new Color(r, g, b);
	}

	public static void line(float x1, float y1, float x2, float y2) {

		setStroke();

		if (drawMode == DrawMode.Corner) {
			glBegin(GL_LINES);

			glVertex2d(map(x1, 0, width, -1, 1), map(y1, 0, height, -1, 1));
			glVertex2d(map(x2, 0, width, -1, 1), map(y2, 0, height, -1, 1));
			glEnd();
		}
	}

	public static float map(float value, float min, float max, float newMin, float newMax) {
		return ((value - min) / (max - min)) * (newMax - newMin) + newMin;
	}

	public static void noFill() {
		fill = null;
	}

	public static void noStroke() {
		stroke = null;
	}

	public static void quad(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {

		line(x1, y1, x2, y2);
		line(x2, y2, x3, y3);
		line(x3, y3, x4, y4);
		line(x4, y4, x1, y1);

		setFill();

		if (drawMode == DrawMode.Corner) {

			glBegin(GL11.GL_QUADS);

			glVertex2d(map(x1, 0, width, -1, 1), map(y1, 0, height, -1, 1));
			glVertex2d(map(x2, 0, width, -1, 1), map(y2, 0, height, -1, 1));
			glVertex2d(map(x3, 0, width, -1, 1), map(y3, 0, height, -1, 1));
			glVertex2d(map(x4, 0, width, -1, 1), map(y4, 0, height, -1, 1));

			glEnd();
		}
	}

	public static void rect(float x, float y, float w, float h) {
		quad(x, y, x, y - h, x + w, y - h, x + w, y);
	}

	public static void rect(float x, float y, float w, float h, RectMode mode) {
		if (mode == RectMode.CORNER) {
			quad(x, y, x, y - h, x + w, y - h, x + w, y);
		} else if (mode == RectMode.CENTER) {
			rect(x - w / 2f, y + h / 2f, w, h);
		}
	}

	public static void setFill() {
		if (fill != null) {
			if (colorMode == ColorMode.Percent) {
				GL11.glColor4f(fill.getR(), fill.getG(), fill.getB(), 1 - fill.getA());
			} else if (colorMode == ColorMode.Value) {
				GL11.glColor4f(fill.getR() / 255f, fill.getG() / 255f, fill.getB() / 255f, 1 - fill.getA() / 255f);
			}
		} else {
			GL11.glColor4f(0f, 0f, 0f, -1f);
		}
	}

	public static void setStroke() {
		if (stroke != null) {
			if (colorMode == ColorMode.Percent) {
				GL11.glColor4f(stroke.getR(), stroke.getG(), stroke.getB(), 1 - stroke.getA());
			} else if (colorMode == ColorMode.Value) {
				GL11.glColor4f(stroke.getR() / 255f, stroke.getG() / 255f, stroke.getB() / 255f,
						1 - stroke.getA() / 255f);
			}
		} else {
			GL11.glColor4f(0f, 0f, 0f, -1f);
		}
	}

	public static void setup(int _width, int _height) {
		width = _width;
		height = _height;
	}

	public static void stroke(Color c) {
		stroke = c.clone();
	}

	public static void stroke(float r, float g, float b) {
		stroke = new Color(r, g, b);
	}

	public static void stroke(float r, float g, float b, float a) {
		stroke = new Color(r, g, b, a);
	}

	public static void triangle(float x1, float y1, float x2, float y2, float x3, float y3) {

		line(x1, y1, x2, y2);

		line(x2, y2, x3, y3);

		line(x3, y3, x1, y1);

		setFill();

		glBegin(GL11.GL_TRIANGLES);

		glVertex2d(x1, y1);
		glVertex2d(x2, y2);
		glVertex2d(x3, y3);

		glEnd();
	}
}