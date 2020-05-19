package org.tat.fni.api.common.emumdata;

public enum StudentAgeType {
	ABOVE_3_YEARS("Above 3 years"), UNDER_3_YEARS("Under 3 years"), PARENT_SQ("Parent Survey Questions");
	private String label;

	private StudentAgeType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
