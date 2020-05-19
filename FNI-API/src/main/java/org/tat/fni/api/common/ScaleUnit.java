package org.tat.fni.api.common;

public class ScaleUnit {
	private int exponent;
	private String[] names;

	public ScaleUnit(int exponent, String... names) {
		this.exponent = exponent;
		this.names = names;
	}

	public int getExponent() {
		return exponent;
	}

	public String getName(int index) {
		return names[index];
	}
}
