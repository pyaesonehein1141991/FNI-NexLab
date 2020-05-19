package org.tat.fni.api.common;

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

import org.tat.fni.api.domain.SurveyQuestionAnswer;

@Entity
@Table(name = TableName.RESOURCEQUESTIONANSWER)
@TableGenerator(name = "RESOURCEQUESTIONANSWER_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "RESOURCEQUESTIONANSWER_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "ResourceQuestionAnswer.findAll", query = "SELECT l FROM ResourceQuestionAnswer l "),
		@NamedQuery(name = "ResourceQuestionAnswer.findById", query = "SELECT l FROM ResourceQuestionAnswer l WHERE l.id = :id"), })
@EntityListeners(IDInterceptor.class)
public class ResourceQuestionAnswer {
	private int value;
	private String result;
	@Version
	private int version;
	private String name;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "RESOURCEQUESTIONANSWER_GEN")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SURVEYQUESTIONANSWERID", referencedColumnName = "ID")
	private SurveyQuestionAnswer surveyQuestionAnswer;

	@Embedded
	private UserRecorder recorder;

	public ResourceQuestionAnswer() {
	}

	public ResourceQuestionAnswer(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SurveyQuestionAnswer getSurveyQuestionAnswer() {
		return surveyQuestionAnswer;
	}

	public void setSurveyQuestionAnswer(SurveyQuestionAnswer surveyQuestionAnswer) {
		this.surveyQuestionAnswer = surveyQuestionAnswer;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getResult() {
		if (result == null || result.isEmpty()) {
			result = "";
		}
		return result;
	}

	public void setResult(String result) {
		this.result = result;
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
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
		result = prime * result + ((surveyQuestionAnswer == null) ? 0 : surveyQuestionAnswer.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + value;
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
		ResourceQuestionAnswer other = (ResourceQuestionAnswer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		if (surveyQuestionAnswer == null) {
			if (other.surveyQuestionAnswer != null)
				return false;
		} else if (!surveyQuestionAnswer.equals(other.surveyQuestionAnswer))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (value != other.value)
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
