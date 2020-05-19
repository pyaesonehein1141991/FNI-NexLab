package org.tat.fni.api.common;

import java.util.ArrayList;
import java.util.List;

import org.tat.fni.api.common.emumdata.InputType;
import org.tat.fni.api.domain.ResourceQuestion;
import org.tat.fni.api.domain.SurveyQuestion;



public class SurveyQuestionDTO extends CommonDTO implements ISorter {
	private static final long serialVersionUID = 1L;
	private boolean existsEntity;
	private String id;
	private boolean deleteFlag;
	private String description;
	private String frontLabel;
	private String behindLabel;
	private String tureLabel;
	private String falseLabel;
	private InputType inputType;
	private List<ProductProcessQuestionLinkDTO> questionLinks;
	private List<ResourceQuestionDTO> resourceQuestionList;
	private int version;
	private String questionNo;

	public SurveyQuestionDTO() {
	}

	public SurveyQuestionDTO(SurveyQuestion surveyQuestion) {
		this.id = surveyQuestion.getId();
		this.deleteFlag = surveyQuestion.isDeleteFlag();
		this.description = surveyQuestion.getDescription();
		this.frontLabel = surveyQuestion.getFrontLabel();
		this.behindLabel = surveyQuestion.getBehindLabel();
		this.tureLabel = surveyQuestion.getTureLabel();
		this.falseLabel = surveyQuestion.getFalseLabel();
		this.inputType = surveyQuestion.getInputType();
		this.version = surveyQuestion.getVersion();
		this.questionNo = surveyQuestion.getQuestionNo();
		for (ProductProcessQuestionLink link : surveyQuestion.getProductProcessQuestionLinkList()) {
			ProductProcessQuestionLinkDTO questionLinkDTO = new ProductProcessQuestionLinkDTO();
			questionLinkDTO.setProductProcess(link.getProductProcess());
			questionLinkDTO.setPriority(link.getPriority());
			this.addProductProcessQuestionLink(questionLinkDTO);
		}
		for (ResourceQuestion resourceQuestion : surveyQuestion.getResourceQuestionList()) {
			this.addResourceQuestion(new ResourceQuestionDTO(resourceQuestion.getName()));
		}

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isExistsEntity() {
		return existsEntity;
	}

	public void setExistsEntity(boolean existsEntity) {
		this.existsEntity = existsEntity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public InputType getInputType() {
		return inputType;
	}

	public void setInputType(InputType inputType) {
		this.inputType = inputType;
	}

	public String getFrontLabel() {
		return frontLabel;
	}

	public void setFrontLabel(String frontLabel) {
		this.frontLabel = frontLabel;
	}

	public String getBehindLabel() {
		return behindLabel;
	}

	public void setBehindLabel(String behindLabel) {
		this.behindLabel = behindLabel;
	}

	public String getTureLabel() {
		return tureLabel;
	}

	public void setTureLabel(String tureLabel) {
		this.tureLabel = tureLabel;
	}

	public String getFalseLabel() {
		return falseLabel;
	}

	public void setFalseLabel(String falseLabel) {
		this.falseLabel = falseLabel;
	}

	public boolean isDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public List<ProductProcessQuestionLinkDTO> getQuestionLinks() {
		if (questionLinks == null) {
			questionLinks = new ArrayList<ProductProcessQuestionLinkDTO>();
		}
		return questionLinks;
	}

	public void setQuestionLinks(List<ProductProcessQuestionLinkDTO> questionLinks) {
		this.questionLinks = questionLinks;
	}

	public List<ResourceQuestionDTO> getResourceQuestionList() {
		if (resourceQuestionList == null) {
			resourceQuestionList = new ArrayList<ResourceQuestionDTO>();
		}
		return resourceQuestionList;
	}

	public void setResourceQuestionList(List<ResourceQuestionDTO> resourceQuestionList) {
		this.resourceQuestionList = resourceQuestionList;
	}

	public void addProductProcessQuestionLink(ProductProcessQuestionLinkDTO questionLink) {
		if (getQuestionLinks() != null) {
			getQuestionLinks().add(questionLink);
		}
	}

	public void addResourceQuestion(ResourceQuestionDTO resourceQuestion) {
		getResourceQuestionList().add(resourceQuestion);
	}

	public void removeResourceQuestion(ResourceQuestionDTO resourceQuestion) {
		if (getResourceQuestionList() != null) {
			getResourceQuestionList().remove(resourceQuestion);
		}
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getQuestionNo() {
		return questionNo;
	}

	public void setQuestionNo(String questionNo) {
		this.questionNo = questionNo;
	}

	@Override
	public String getRegistrationNo() {
		return id + "";
	}

}
