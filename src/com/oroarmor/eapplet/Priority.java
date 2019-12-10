package com.oroarmor.eapplet;

import java.util.Comparator;

public interface Priority {
	public int getPriority();

	public void setPriority(int priority);

	public static class PriorityComparator implements Comparator<Priority> {
		@Override
		public int compare(Priority o1, Priority o2) {
			return o1.getPriority() - o2.getPriority();
		}
	}
}
