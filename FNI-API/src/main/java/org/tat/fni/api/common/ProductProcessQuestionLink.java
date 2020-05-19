package org.tat.fni.api.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.tat.fni.api.domain.SurveyQuestion;

@Entity
@Table(name = TableName.PRODUCT_PROCESS_QUESTION_LINK)
@TableGenerator(name = "PRODUCT_PROCESS_QUESTION_LINK_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "PRODUCT_PROCESS_QUESTION_LINK_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "ProductProcessQuestionLink.findById", query = "SELECT a FROM ProductProcessQuestionLink a WHERE a.id = :id") })
@EntityListeners(IDInterceptor.class)
public class ProductProcessQuestionLink implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "OPTIONAL")
	private boolean option;
	private int priority;
	@Version
	private int version;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "PRODUCT_PROCESS_QUESTION_LINK_GEN")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTPROCESS_ID", referencedColumnName = "ID")
	private ProductProcess productProcess;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SURVEYQUESTION_ID", referencedColumnName = "ID")
	private SurveyQuestion surveyQuestion;

	@Embedded
	private UserRecorder recorder;

	public ProductProcessQuestionLink() {
	}

	public ProductProcessQuestionLink(SurveyQuestion surveyQuestion) {
		this.surveyQuestion = surveyQuestion;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (option ? 1231 : 1237);
		result = prime * result + priority;
		result = prime * result + ((productProcess == null) ? 0 : productProcess.hashCode());
		result = prime * result + ((surveyQuestion == null) ? 0 : surveyQuestion.hashCode());
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
		ProductProcessQuestionLink other = (ProductProcessQuestionLink) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (option != other.option)
			return false;
		if (priority != other.priority)
			return false;
		if (productProcess == null) {
			if (other.productProcess != null)
				return false;
		} else if (!productProcess.equals(other.productProcess))
			return false;
		if (surveyQuestion == null) {
			if (other.surveyQuestion != null)
				return false;
		} else if (!surveyQuestion.equals(other.surveyQuestion))
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

}
