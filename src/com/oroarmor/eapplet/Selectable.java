package com.oroarmor.eapplet;

public interface Selectable extends Priority {
	public boolean getSelected();

	public void setSelected(boolean selected);

	public boolean inBounds(float x, float y);
}
