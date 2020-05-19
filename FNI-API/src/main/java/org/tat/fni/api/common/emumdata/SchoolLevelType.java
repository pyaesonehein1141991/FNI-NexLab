package org.tat.fni.api.common.emumdata;

public enum SchoolLevelType {

	PRE_SCHOOL("Pre School"), PRIMARY("Primary"), MIDDLE("Middle"), HIGH("High");

	private String label;

	private SchoolLevelType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
