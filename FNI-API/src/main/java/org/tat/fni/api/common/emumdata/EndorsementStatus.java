package org.tat.fni.api.common.emumdata;

public enum EndorsementStatus {
	REPLACE("Replace"),

	TERMINATE_BY_CUSTOMER("Terminate By Customer"),

	TERMINATE_BY_INSURER("Terminate By Insurer"),

	TERMINATE("Terminate"),

	TERMINATE_BY_RENEWAL("Terminate By Renewal"),

	EDIT("Edit"),

	NEW("New");

	private String label;

	private EndorsementStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
