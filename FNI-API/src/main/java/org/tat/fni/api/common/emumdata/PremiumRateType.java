package org.tat.fni.api.common.emumdata;

public enum PremiumRateType {

	USER_DEFINED_PREMIUM("User Defined Premium"),

	BASED_ON_MAINCOVER_SUMINSURED("Based on Main Cover Sum Insured"),

	BASED_ON_OWN_SUMINSURED("Based on Own Sum Insured"),

	BASED_ON_PREMIUM("Based on Premium"),

	PER_UNIT("Per Unit"),

	FIXED("Fixed");

	private String label;

	private PremiumRateType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
