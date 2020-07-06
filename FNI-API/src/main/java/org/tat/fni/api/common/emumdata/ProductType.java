package org.tat.fni.api.common.emumdata;

public enum ProductType {
	
	LIFE("LIFE"), MEDICAL("MEDICAL");

	private String label;

	private ProductType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
