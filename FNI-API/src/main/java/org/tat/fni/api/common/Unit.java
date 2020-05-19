package org.tat.fni.api.common;

public class Unit {
	String key;
	int value;

	public Unit() {

	}

	public Unit(String key, int value) {
		this.key = key;
		this.value = value;
	}

	public String getValueString() {
		return Integer.toString(value);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
