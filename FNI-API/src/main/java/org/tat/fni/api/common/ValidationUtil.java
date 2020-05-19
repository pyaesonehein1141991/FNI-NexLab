package org.tat.fni.api.common;

public class ValidationUtil {

	private ValidationUtil() {
	}

	public static boolean isStringEmpty(String value) {
		return value == null || value.isEmpty();
	}

	public static boolean isDoubleEmpty(double value) {
		return value == 0.00;
	}

}
