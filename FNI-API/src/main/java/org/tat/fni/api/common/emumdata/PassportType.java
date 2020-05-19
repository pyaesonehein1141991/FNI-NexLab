package org.tat.fni.api.common.emumdata;

public enum PassportType {
	WORKING("Working"), VISITING("Visiting"), OTHERS("Others");

	private String label;

	private PassportType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
