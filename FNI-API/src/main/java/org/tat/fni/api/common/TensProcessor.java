package org.tat.fni.api.common;

import org.tat.fni.api.common.utils.UnitProcessor;

public class TensProcessor extends AbstractProcessor {
	static private final String[] TOKENS = new String[] { "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety" };
	static private final String UNION_SEPARATOR = "-";
	private UnitProcessor unitProcessor = new UnitProcessor();

	@Override
	public String getName(String value) {
		StringBuilder buffer = new StringBuilder();
		boolean tensFound = false;
		int number;
		if (value.length() > 3) {
			number = Integer.valueOf(value.substring(value.length() - 3), 10);
		} else {
			number = Integer.valueOf(value, 10);
		}
		number %= 100; // keep only two digits
		if (number >= 20) {
			buffer.append(TOKENS[(number / 10) - 2]);
			number %= 10;
			tensFound = true;
		} else {
			number %= 20;
		}
		if (number != 0) {
			if (tensFound) {
				buffer.append(UNION_SEPARATOR);
			}
			buffer.append(unitProcessor.getName(number));
		}
		return buffer.toString();
	}

	@Override
	public String getNameWithDecimal(String value, String inwordOne, String inwordTwo) {
		// TODO Auto-generated method stub
		return null;
	}

}
