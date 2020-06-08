package org.tat.fni.api.common.emumdata;

public enum ReferenceType {

	/* Health */
	HEALTH("Health"),

	MICRO_HEALTH("MicroHealth"),

	CRITICAL_ILLNESS("CriticalIllness"),

	/* Life */
	FARMER("Farmer"),

	SNAKE_BITE("SnakeBite"),

	PA("PersonalAccident"),

	SHORT_ENDOWMENT_LIFE("ShortEndowmentLife"),

	GROUP_LIFE("GroupLife"),

	ENDOWMENT_LIFE("EndowmentLife"),

	SPORT_MAN("SportMan"),

	SPORT_MAN_ABROAD("SportManAbroad"),

	/* Transaction */
	CRITICAL_ILLNESS_POLICY_BILL_COLLECTION("CriticalIllnessPolicyBIllCollection"),

	HEALTH_POLICY_BILL_COLLECTION("HealthPolicyBillCollection"),

	MICRO_HEALTH_POLICY_BILL_COLLECTION("MicroHealthPolicyBIllCollection"),

	AGENT_COMMISSION("AgentCommission"),

	/*
	 * Claim
	 */
	LIFESURRENDER("LifeSurrender"),

	LIFE_PAIDUP_PROPOSAL("LifePaidUp"),

	LIFE_DIS_CLAIM("LifeDisClaim"),

	LIFE_DEALTH_CLAIM("LifeDealthClaim"),

	LIFE_CLAIM("LifeClaim"),

	HEALTH_CLAIM("HealthClaim"),

	STUDENT_LIFE("Student Life"),

	PUBLIC_TERM_LIFE("PublicTermLife");

	private String label;

	private ReferenceType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
