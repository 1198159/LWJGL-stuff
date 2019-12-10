package com.oroarmor.eapplet;

import java.util.ArrayList;
import java.util.Iterator;

public class Clicker {

	public static ArrayList<Clickable> objects = new ArrayList<Clickable>(1);

	public static Clickable currentSelected;

	public static void addClickable(Clickable... cs) {
		for (Clickable c : cs) {
			objects.add(c);
		}
		objects.sort(new Priority.PriorityComparator());
	}

	public static void clickObjects() {
		Iterator<Clickable> iter = objects.iterator();
		currentSelected = null;

		while (iter.hasNext()) {
			Clickable c = iter.next();
			if (c.inBounds(EApplet.mouseX, EApplet.mouseY)) {
				currentSelected = c;
			} else {
				c.setSelected(false);
			}
		}

		if (currentSelected != null) {
			currentSelected.setSelected(true);
			currentSelected.onClick();
		}

	}

	public static void holdObjects() {
		Iterator<Clickable> iter = objects.iterator();
		while (iter.hasNext()) {
			Clickable c = iter.next();
			if (c.getSelected()) {
				c.onHold();
			}
		}
	}

	public static void releaseObjects() {
		Iterator<Clickable> iter = objects.iterator();
		currentSelected = null;

		while (iter.hasNext()) {
			Clickable c = iter.next();
			if (c.inBounds(EApplet.mouseX, EApplet.mouseY)) {
				currentSelected = c;
			}
		}

		if (currentSelected != null) {
			currentSelected.setSelected(false);
			currentSelected.onHold();
		}

	}
}
