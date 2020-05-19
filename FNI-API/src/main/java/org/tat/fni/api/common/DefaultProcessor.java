package org.tat.fni.api.common;

import org.tat.fni.api.common.emumdata.Scale;

public class DefaultProcessor extends AbstractProcessor {
	static public Scale SCALE = Scale.SHORT;
	static private String MINUS = "Minus";
	static private String UNION_AND = "And";
	static private String ZERO_TOKEN = "Zero";
	private AbstractProcessor processor = new CompositeBigProcessor(63);

	@Override
	public String getName(String value) {
		boolean negative = false;
		if (value.startsWith("-")) {
			negative = true;
			value = value.substring(1);
		}
		int decimals = value.indexOf(".");
		String decimalValue = null;
		if (0 <= decimals) {
			decimalValue = value.substring(decimals + 1);
			value = value.substring(0, decimals);
		}
		String name = processor.getName(value);
		if (name.isEmpty()) {
			name = ZERO_TOKEN;
		} else if (negative) {
			name = MINUS.concat(SEPARATOR).concat(name);
		}
		if (!(null == decimalValue || decimalValue.isEmpty() || decimalValue.equals("00"))) {
			/*
			 * name =
			 * name.concat(SEPARATOR).concat(UNION_AND).concat(SEPARATOR).
			 * concat(processor.getName(decimalValue)).concat(SEPARATOR)
			 * .concat(SCALE.getName(-decimalValue.length()));
			 */
			name = name.concat(SEPARATOR).concat(UNION_AND).concat(SEPARATOR).concat(processor.getName(decimalValue));
		}
		return name;
	}

	@Override
	public String getNameWithDecimal(String value, String inwordOne, String inwordTwo) {
		boolean negative = false;
		if (value.startsWith("-")) {
			negative = true;
			value = value.substring(1);
		}
		int decimals = value.indexOf(".");
		String decimalValue = null;
		if (0 <= decimals) {
			decimalValue = value.substring(decimals + 1);
			value = value.substring(0, decimals);
		}
		String name = processor.getName(value);
		if (name.isEmpty()) {
			name = ZERO_TOKEN;
		} else if (negative) {
			name = MINUS.concat(SEPARATOR).concat(name);
		}
		if (!(null == decimalValue || decimalValue.isEmpty() || decimalValue.equals("00"))) {
			name = name.concat(SEPARATOR).concat(UNION_AND).concat(SEPARATOR).concat(inwordTwo).concat(SEPARATOR).concat(processor.getName(decimalValue));
		}
		return inwordOne.concat(SEPARATOR) + name;
	}
}
