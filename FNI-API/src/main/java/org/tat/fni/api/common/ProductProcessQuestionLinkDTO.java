package org.tat.fni.api.common;

import org.tat.fni.api.domain.SurveyQuestion;

public class ProductProcessQuestionLinkDTO extends CommonDTO {
	private boolean exitingEntity;
	private boolean option;
	private int priority;
	private int version;
	private String id;
	private ProductProcess productProcess;
	private SurveyQuestion surveyQuestion;

	public ProductProcessQuestionLinkDTO() {
		id = System.nanoTime() + "";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ProductProcessQuestionLinkDTO(SurveyQuestion surveyQuestion) {
		id = System.nanoTime() + "";
		this.surveyQuestion = surveyQuestion;
	}

	public ProductProcess getProductProcess() {
		return productProcess;
	}

	public void setProductProcess(ProductProcess productProcess) {
		this.productProcess = productProcess;
	}

	public SurveyQuestion getSurveyQuestion() {
		return surveyQuestion;
	}

	public void setSurveyQuestion(SurveyQuestion surveyQuestion) {
		this.surveyQuestion = surveyQuestion;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public boolean isExitingEntity() {
		return exitingEntity;
	}

	public void setExitingEntity(boolean exitingEntity) {
		this.exitingEntity = exitingEntity;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public boolean isOption() {
		return option;
	}

	public void setOption(boolean option) {
		this.option = option;
	}
}
