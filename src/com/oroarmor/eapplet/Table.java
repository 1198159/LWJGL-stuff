package com.oroarmor.eapplet;

import java.util.ArrayList;
import java.util.HashMap;

public class Table<E, V> {

	public ArrayList<E> setOne;
	public ArrayList<V> setTwo;

	public Table() {
		setOne = new ArrayList<E>();
		setTwo = new ArrayList<V>();
	}

	public void add(E e, V v) {
		setOne.add(e);
		setTwo.add(v);
	}

	public HashMap<E, V> get(int i) {
		HashMap<E, V> temp = new HashMap<E, V>();
		temp.put(setOne.get(i), setTwo.get(i));
		return temp;
	}
}
