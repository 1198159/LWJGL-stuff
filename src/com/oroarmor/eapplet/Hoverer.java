package com.oroarmor.eapplet;

import java.util.ArrayList;
import java.util.Iterator;

public class Hoverer {

	public static ArrayList<Hoverable> objects = new ArrayList<Hoverable>(1);

	public static Hoverable currentSelected;

	public static void addHoverable(Hoverable... hs) {
		for (Hoverable h : hs) {
			objects.add(h);
		}
		objects.sort(new Priority.PriorityComparator());
	}

	public static void hoverObjects() {
		Iterator<Hoverable> iter = objects.iterator();
		currentSelected = null;

		while (iter.hasNext()) {
			Hoverable h = iter.next();
			if (h.inBounds(EApplet.mouseX, EApplet.mouseY)) {
				currentSelected = h;
			}
		}

		iter = objects.iterator();
		while (iter.hasNext()) {
			Hoverable h = iter.next();
			if (h != currentSelected) {
				h.onHoverOff();
			}
		}

		if (currentSelected != null) {
			currentSelected.onHover();
		}

	}
}
