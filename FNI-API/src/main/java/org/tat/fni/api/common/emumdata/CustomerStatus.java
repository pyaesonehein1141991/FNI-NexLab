package org.tat.fni.api.common.emumdata;

public enum CustomerStatus {
	CONTRACTOR("CONTRACTOR"), INSUREDPERSON("INSUREDPERSON"), GUARDIAN("GUARDIAN");

	private String label;

	private CustomerStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
