package org.tat.fni.api.common;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.tat.fni.api.domain.Currency;

public abstract class AbstractProcessor {
	protected final String SEPARATOR = " ";
	protected final int NO_VALUE = -1;

	protected List<Integer> getDigits(long value) {
		ArrayList<Integer> digits = new ArrayList<>();
		if (value == 0) {
			digits.add(0);
		} else {
			while (value > 0) {
				digits.add(0, (int) value % 10);
				value /= 10;
			}
		}
		return digits;
	}

	public String getName(long value) {
		return getName(Long.toString(value));
	}

	public String getName(double value) {
		String dString = new DecimalFormat("#.00").format(value);
		return getName(dString);
	}

	public String getNameWithDecimal(double value, Currency currency) {
		String dString = new DecimalFormat("#.00").format(value);
		return getNameWithDecimal(dString, currency.getInwordDesp1(), currency.getInwordDesp2());

	}

	abstract public String getName(String value);

	abstract public String getNameWithDecimal(String value, String inwordOne, String inwordTwo);
}