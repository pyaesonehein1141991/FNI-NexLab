package org.tat.fni.api.common.emumdata;

public enum SchoolType {

	PRIVATE_SCHOOL("Private School"), GOVERRNMENT_SCHOOL("Government School"), INTERNATIONAL_SCHOOL("International School");

	private SchoolType(String label) {
		this.label = label;

	}

	private String label;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
