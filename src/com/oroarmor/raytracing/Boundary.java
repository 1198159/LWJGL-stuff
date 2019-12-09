package com.oroarmor.raytracing;

import com.oroarmor.eapplet.Color;
import com.oroarmor.eapplet.EApplet;
import com.oroarmor.physics.Vector;
import static com.oroarmor.eapplet.Drawer.*;
public class Boundary {
	Vector a, b;
	public Color c;

	public Boundary(float x1, float y1, float x2, float y2) {
		this.a = new Vector(x1, y1);
		this.b = new Vector(x2, y2);
		c = new Color((float) Math.random() * 255, (float) Math.random() * 255, (float) Math.random() * 255);
	}

	public void show(EApplet applet) {
		stroke(c);
		line(this.a.x, this.a.y, this.b.x, this.b.y);
	}
}
