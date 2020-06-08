package org.tat.fni.api.common.emumdata;

public enum InsuranceType {

	LIFE("Life"),

	STUDENT_LIFE("Student Life"),

	HEALTH("Health"),

	PERSON_TRAVEL("Preson Travel"),

	FARMER("Farmer"),

	MOTOR("MOTOR"),

	CASH_IN_SAFE("CASH_IN_SAFE"),

	FIRE("FIRE"),

	SHORT_ENDOWMENT_LIFE("Short Endowment Life"),

	PUBLIC_TERM_LIFE("Public Term Life"),

	SPORTMAN("Sport Man"),

	SPORTMANABROAD("Sport Man Abroad");

	private String label;

	private InsuranceType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}