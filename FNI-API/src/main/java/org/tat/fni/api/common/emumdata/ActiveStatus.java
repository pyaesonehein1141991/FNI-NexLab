package org.tat.fni.api.common.emumdata;

public enum ActiveStatus {
	ACTIVATE("activate"), DEACTIVATE("deactivate"), CONFIGURE("configure");

	private String label;

	private ActiveStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
