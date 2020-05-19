package org.tat.fni.api.common.emumdata;

public enum SurveyType {
	MEDICAL_UNDERWRITING_SURVEY("medical_underwriting_survey"),

	MEDICAL_CLAIM_SURVEY("medical_Claim_survey"),

	SHORT_ENDOWMENT_LIFE_SURVEY("short_endowment_life_underwriting_survey"),

	FIRE_UNDERWRITING_SURVEY("fire_underwriting_survey"), STUDENT_LIFE_PARENT_SURVEY("Student_Life_Parent_Underwriting_Survey"),

	STUDENT_LIFE_CHILD_SURVEY("Student_Life_Child_Underwriting_Survey"),

	PUBLIC_TERM_LIFE_OVER_5_MILLION("Public_Term_Life_Over_5_Million_Underwriting_Survey"),

	PUBLIC_TERM_LIFE_OVER_3_MILLION("Public_Term_Life_Over_3_Million_Underwriting_Survey");

	private String label;

	private SurveyType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
