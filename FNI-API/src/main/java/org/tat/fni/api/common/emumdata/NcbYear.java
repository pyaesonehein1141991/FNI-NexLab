package org.tat.fni.api.common.emumdata;

public enum NcbYear {

	FIRST_YEAR("First Year"), SECOND_YEAR("Second Year"), THIRD_YEAR("Third Year"), ABOVE_THIRD_YEAR("Above Third Year"), FIXED("Fixed");

	private String label;

	private NcbYear(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
