package org.tat.fni.api.common.emumdata;

public enum IdType {
	NRCNO("NRCNO"), FRCNO("FRCNO"), PASSPORTNO("PASSPORTNO"), STILL_APPLYING("Still Applying");

	private String label;

	private IdType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
