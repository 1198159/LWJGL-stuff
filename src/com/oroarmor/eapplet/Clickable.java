package com.oroarmor.eapplet;

public interface Clickable extends Selectable {
	public void onClick();

	public void onHold();

	public void onRelease();
}
