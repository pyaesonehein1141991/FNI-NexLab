package org.tat.fni.api.common.emumdata;

public enum InputType {
	NONE("None"), TEXT("Text"), NUMBER("Number"), DATE("Date"), BOOLEAN("Boolean"), SELECT_ONE_RADIO("Select One Radio"), SELECT_ONE_MENU("Select One Menu"), SELECT_MANY_CHECKBOX(
			"Select Many Checkbox"), SELECT_MANY_MENU("Select Many Menu");

	private String label;

	private InputType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
