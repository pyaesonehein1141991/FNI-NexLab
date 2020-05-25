package org.tat.fni.api.domain;

public enum PolicyStatus {
	DELETE("DELETE"),

	UPDATE("UPDATE"),

	TERMINATE("TERMINATE"),

	PREPAID("PREPAID"),

	PAID("PAID"),

	DEACTIVATE("DEACTIVATE"),

	REINSTATE("REINSTATE"),

	SURRENDER("SURRENDER"),

	PAIDUP("PAIDUP"),

	LOAN("LOAN");

	private String label;

	private PolicyStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
