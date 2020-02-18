package com.oroarmor.pathfollow;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class Position {
	public double x, y, a;

	public Position(double xv, double yv, double rotation) {
		x = xv;
		y = yv;
		a = rotation;
	}

	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(10));
		// g.drawRect((int)(Math.cos(a)*5+x), (int)(Math.sin(a)*20+y),
		// (int)(Math.cos(a+Math.PI)*5+x), (int)(Math.sin(a+Math.PI)*20+y));
		g2.drawLine((int) x, (int) y, (int) (Math.cos(-a) * 50 + x), (int) (Math.sin(-a) * 50 + y));
		g2.drawLine( (int) (Math.cos(-a+Math.PI/2) * 10 + x), (int) (Math.sin(-a+Math.PI/2) * 10 + y), (int) (Math.cos(-a-Math.PI/2) * 10 + x), (int) (Math.sin(-a-Math.PI/2) * 10 + y));
	}
	
	@Override
	public String toString() {
		return "[x="+x+"],[y="+y+"],[a="+Math.toDegrees(a)+"]";
	}
}
