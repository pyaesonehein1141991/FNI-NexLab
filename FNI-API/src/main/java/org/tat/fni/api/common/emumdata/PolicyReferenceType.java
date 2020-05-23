package org.tat.fni.api.common.emumdata;

import java.util.Arrays;
import java.util.List;

public enum PolicyReferenceType {

	PA_POLICY("Personal Accident Policy"),

	FARMER_POLICY("Farmer Policy"),

	SNAKE_BITE_POLICY("Snake Bite Policy"),

	GROUP_LIFE_POLICY("Group Life Policy"),

	SPORT_MAN_POLICY("Sport Man Policy"),

	ENDOWNMENT_LIFE_POLICY("Endowment Policy"),

	LIFE_BILL_COLLECTION("Life Bill Collection"),

	SHORT_ENDOWMENT_LIFE_POLICY("Short Term Endowment Policy"),

	SHORT_ENDOWMENT_LIFE_BILL_COLLECTION("Short Term Endowment Bill Collection"),

	STUDENT_LIFE_POLICY("Student Life Policy"),

	PUBLIC_TERM_LIFE_POLICY("Public Term Life Policy"),

	STUDENT_LIFE_POLICY_BILL_COLLECTION("Student Life Policy Bill Collection"),

	PUBLIC_TERM_LIFE_POLICY_BILL_COLLECTION("Public Term Life Policy Bill Collection"),

	LIFE_CLAIM("Life Claim"),

	LIFE_DIS_CLAIM("Life Disablity Claim"),

	PA_LIFE_CLAIM("Personal Accident Life Claim"), ENDOWNMENT_LIFE_CLAIM("Personal Accident Life Claim"), SHORT_TERM_LIFE_CLAIM("Personal Accident Life Claim"), STUDENT_LIFE_CLAIM(
			"Student Life Claim"), PUBLIC_TERM_LIFE_CLAIM(
					"Public Term Life Claim"), SNAKE_BITE_LIFE_CLAIM("Snake Bite Claim"), SPORT_MAN_CLAIM("Sport Man Claim"), GROUP_LIFE_CLAIM("Group Life Claim"),

	LIFE_SURRENDER_CLAIM("Life Surrender Claim"),

	LIFE_PAIDUP_CLAIM("Life PaidUp Claim"),

	HEALTH_POLICY("Health Policy"),

	HEALTH_POLICY_BILL_COLLECTION("Health Bill Collection"),

	HEALTH_CLAIM("Health Claim"),

	HEALTH_DEATH_CLAIM("Health Death Claim"),

	MICRO_HEALTH_POLICY("Micro Health Policy"),

	// MICRO_HEALTH_POLICY_BILL_COLLECTION("Micro Health Bill Collection"),

	CRITICAL_ILLNESS_POLICY("Critical Illness Policy"),

	CRITICAL_ILLNESS_POLICY_BILL_COLLECTION("Critical Illness Bill Collection"),

	MEDICAL_CLAIM("MEDICAL_CLAIM"),

	AGENT_COMMISSION("Agent Commission");

	private String label;

	private PolicyReferenceType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static List<PolicyReferenceType> getMedicalPolicyReference() {
		return Arrays.asList(HEALTH_POLICY, MICRO_HEALTH_POLICY, CRITICAL_ILLNESS_POLICY);
	}

}
