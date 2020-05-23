package org.tat.fni.api.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.ProductProcessQuestionLink;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;
import org.tat.fni.api.common.emumdata.InputType;
import org.tat.fni.api.common.interfaces.ISorter;


@Entity
@Table(name = TableName.SURVEYQUESTION)
@TableGenerator(name = "SURVEYQUESTION_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "SURVEYQUESTION_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "SurveyQuestion.findAll", query = "SELECT a FROM SurveyQuestion a WHERE a.deleteFlag = false"),
		@NamedQuery(name = "SurveyQuestion.findById", query = "SELECT a FROM SurveyQuestion a WHERE a.id = :id") })
@EntityListeners(IDInterceptor.class)
public class SurveyQuestion implements Serializable, ISorter {
	private static final long serialVersionUID = 1L;
	private boolean deleteFlag;
	@Version
	private int version;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SURVEYQUESTION_GEN")
	private String id;
	private String description;
	private String frontLabel;
	private String behindLabel;
	private String tureLabel;
	private String falseLabel;
	private String questionNo;

	@Enumerated(value = EnumType.STRING)
	private InputType inputType;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "surveyQuestion", orphanRemoval = true)
	private List<ProductProcessQuestionLink> productProcessQuestionLinkList;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "SURVEYQUESTION_ID", referencedColumnName = "ID")
	private List<ResourceQuestion> resourceQuestionList;

	@Embedded
	private UserRecorder recorder;

	public SurveyQuestion() {
		super();
	}

	public SurveyQuestion(SurveyQuestion sq) {
		this.description = sq.getDescription();
		this.frontLabel = sq.getFrontLabel();
		this.behindLabel = sq.getBehindLabel();
		this.tureLabel = sq.getTureLabel();
		this.falseLabel = sq.getFalseLabel();
		this.inputType = sq.getInputType();
		this.questionNo = sq.getQuestionNo();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public List<ProductProcessQuestionLink> getProductProcessQuestionLinkList() {
		if (productProcessQuestionLinkList == null) {
			productProcessQuestionLinkList = new ArrayList<ProductProcessQuestionLink>();
		}
		return productProcessQuestionLinkList;
	}

	public void setProductProcessQuestionLinkList(List<ProductProcessQuestionLink> productProcessQuestionLinkList) {
		this.productProcessQuestionLinkList = productProcessQuestionLinkList;
	}

	public List<ResourceQuestion> getResourceQuestionList() {
		if (resourceQuestionList == null) {
			resourceQuestionList = new ArrayList<ResourceQuestion>();
		}
		return resourceQuestionList;
	}

	public void setResourceQuestionList(List<ResourceQuestion> resourceQuestionList) {
		this.resourceQuestionList = resourceQuestionList;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public void addProductProcessQuestionLink(ProductProcessQuestionLink ppqLink) {
		ppqLink.setSurveyQuestion(this);
		if (getProductProcessQuestionLinkList() != null) {
			getProductProcessQuestionLinkList().add(ppqLink);
		}
	}

	public void addResourceQuestion(ResourceQuestion resourceQuestion) {
		getResourceQuestionList().add(resourceQuestion);
	}

	public String getQuestionNo() {
		return questionNo;
	}

	public void setQuestionNo(String questionNo) {
		this.questionNo = questionNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((behindLabel == null) ? 0 : behindLabel.hashCode());
		result = prime * result + (deleteFlag ? 1231 : 1237);
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((falseLabel == null) ? 0 : falseLabel.hashCode());
		result = prime * result + ((frontLabel == null) ? 0 : frontLabel.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((inputType == null) ? 0 : inputType.hashCode());
		result = prime * result + ((questionNo == null) ? 0 : questionNo.hashCode());
		result = prime * result + ((resourceQuestionList == null) ? 0 : resourceQuestionList.hashCode());
		result = prime * result + ((tureLabel == null) ? 0 : tureLabel.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + version;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SurveyQuestion other = (SurveyQuestion) obj;
		if (behindLabel == null) {
			if (other.behindLabel != null)
				return false;
		} else if (!behindLabel.equals(other.behindLabel))
			return false;
		if (deleteFlag != other.deleteFlag)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (falseLabel == null) {
			if (other.falseLabel != null)
				return false;
		} else if (!falseLabel.equals(other.falseLabel))
			return false;
		if (frontLabel == null) {
			if (other.frontLabel != null)
				return false;
		} else if (!frontLabel.equals(other.frontLabel))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (inputType != other.inputType)
			return false;
		if (questionNo == null) {
			if (other.questionNo != null)
				return false;
		} else if (!questionNo.equals(other.questionNo))
			return false;
		if (resourceQuestionList == null) {
			if (other.resourceQuestionList != null)
				return false;
		} else if (!resourceQuestionList.equals(other.resourceQuestionList))
			return false;
		if (tureLabel == null) {
			if (other.tureLabel != null)
				return false;
		} else if (!tureLabel.equals(other.tureLabel))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	@Override
	public String getRegistrationNo() {
		return this.id;
	}

}
