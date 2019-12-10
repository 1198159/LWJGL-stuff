package com.oroarmor.eapplet;

public abstract class Button implements Drawable, Hoverable, Clickable {

	public float x, y;
	public int priority = Integer.MAX_VALUE;
	public boolean selected = false;

	public Button(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int getPriority() {
		return priority;
	}

	@Override
	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
	public boolean getSelected() {
		return selected;
	}

	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public static void addButton(Button... bs) {
		for (Button b : bs) {
			Drawer.addDrawable(b);
			Clicker.addClickable(b);
			Hoverer.addHoverable(b);
		}
	}
}
