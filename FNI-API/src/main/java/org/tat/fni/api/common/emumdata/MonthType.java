package org.tat.fni.api.common.emumdata;

public enum MonthType {
	JAN("January"), FEB("February"), MAR("March"), APR("April"), MAY("May"), JUN("June"), JUL("July"), AUG("August"), SEP("September"), OCT("October"), NOV("November"), DEC(
			"December");

	private String label;

	private MonthType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public int getValue() {
		int value = 0;
		switch (this) {
			case JAN:
				value = 0;
				break;
			case FEB:
				value = 1;
				break;
			case MAR:
				value = 2;
				break;
			case APR:
				value = 3;
				break;
			case MAY:
				value = 4;
				break;
			case JUN:
				value = 5;
				break;
			case JUL:
				value = 6;
				break;
			case AUG:
				value = 7;
				break;
			case SEP:
				value = 8;
				break;
			case OCT:
				value = 9;
				break;
			case NOV:
				value = 10;
				break;
			case DEC:
				value = 11;
				break;

		}
		return value;
	}
}
