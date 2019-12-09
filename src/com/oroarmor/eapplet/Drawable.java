package com.oroarmor.eapplet;

import java.util.Comparator;

public interface Drawable {
	public void draw();
	public int getPriority();
	public void setPriority(int priority);
	
	public static class DrawableComparator implements Comparator<Drawable>{
		@Override
		public int compare(Drawable o1, Drawable o2) {
			return o1.getPriority() - o2.getPriority();
		}
	}
}
