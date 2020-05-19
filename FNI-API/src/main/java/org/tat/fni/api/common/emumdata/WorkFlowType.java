package org.tat.fni.api.common.emumdata;

public enum WorkFlowType {
	PUBLIC_TERM_LIFE("PUBLIC_TERM_LIFE"),

	STUDENT_LIFE("STUDENT_LIFE"),

	LIFE("Life"),

	FIRE("Fire"),

	MOTOR("Motor"),

	AGENT_COMMISSION("Agent Commission"),

	COINSURANCE("Co-insurance"),

	CASH_IN_SAFE("Cash In Safe"),

	FEDILITY("Fidelity"),

	CASHIN_TRANSIT("Cash In Transit"),

	SNAKE_BITE("Snake Bite"),

	MEDICAL_INSURANCE("Medical"),

	SPECIAL_TRAVEL("Special Travel"),

	CARGO("Cargo"),

	OVERSEASCARGO("OverseasCargo"),

	LIFESURRENDER("Life Surrender"),

	LIFE_PAIDUP("Life PaidUp"),

	PERSONAL_ACCIDENT("Personal Accident"),

	SHORT_ENDOWMENT("Short Term Endowment Life"),

	TRAVEL("Travel"),

	FARMER("Farmer"),

	MARINEHULL("Marine Hull"),

	REINSURANCE("Re-insurance");

	private String label;

	private WorkFlowType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
