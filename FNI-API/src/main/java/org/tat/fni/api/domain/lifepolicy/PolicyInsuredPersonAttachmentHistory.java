package org.tat.fni.api.domain.lifepolicy;

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

import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;
import org.tat.fni.api.domain.InsuredPersonAttachment;

@Entity
@Table(name = TableName.LIFEPOLICYINSUREDPERSONATTACHMENTHISTORY)
@TableGenerator(name = "LPOLINSUREDPERSONATTACHMENTHIS_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LPOLINSUREDPERSONATTACHMENTHIS_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "InsuredPersonAttachment.findAll", query = "SELECT m FROM InsuredPersonAttachment m "),
		@NamedQuery(name = "InsuredPersonAttachment.findById", query = "SELECT m FROM InsuredPersonAttachment m WHERE m.id = :id") })
@EntityListeners(IDInterceptor.class)
public class PolicyInsuredPersonAttachmentHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LPOLINSUREDPERSONATTACHMENTHIS_GEN")
	private String id;

	private String name;
	private String filePath;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "LIFEPOLICYINSUREDPERSONID", referencedColumnName = "ID")
//	private PolicyInsuredPersonHistory policyInsuredPerson;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public PolicyInsuredPersonAttachmentHistory() {
	}

	public PolicyInsuredPersonAttachmentHistory(InsuredPersonAttachment attachment) {
		this.name = attachment.getName();
		this.filePath = attachment.getFilePath();
	}

	public PolicyInsuredPersonAttachmentHistory(PolicyInsuredPersonAttachment attachment) {
		this.name = attachment.getName();
		this.filePath = attachment.getFilePath();
	}

	public PolicyInsuredPersonAttachmentHistory(String name, String filePath) {
		this.name = name;
		this.filePath = filePath;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

//	public PolicyInsuredPersonHistory getPolicyInsuredPerson() {
//		return policyInsuredPerson;
//	}
//
//	public void setPolicyInsuredPerson(PolicyInsuredPersonHistory policyInsuredPerson) {
//		this.policyInsuredPerson = policyInsuredPerson;
//	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((filePath == null) ? 0 : filePath.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		PolicyInsuredPersonAttachmentHistory other = (PolicyInsuredPersonAttachmentHistory) obj;
		if (filePath == null) {
			if (other.filePath != null)
				return false;
		} else if (!filePath.equals(other.filePath))
			return false;
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
