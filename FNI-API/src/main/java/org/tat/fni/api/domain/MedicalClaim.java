package org.tat.fni.api.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;


@Entity
@Table(name = TableName.MEDICALCLAIM)
@TableGenerator(name = "MEDICALCLAIM_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "MEDICALCLAIM_GEN", allocationSize = 10)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "CLAIMROLE", discriminatorType = DiscriminatorType.STRING)
@EntityListeners(IDInterceptor.class)
public class MedicalClaim implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5071326242857209048L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "MEDICALCLAIM_GEN")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLAIMPROPOSAL_ID", referencedColumnName = "ID")
	private MedicalClaimProposal medicalClaimProposal;

	@Column(name = "APPROVE")
	private boolean isApproved;

	@Column(name = "REJECTREASON")
	private String rejectReason;

	@Column(name = "CLAIMROLE", insertable = false, updatable = false)
	private String claimRole;

	@Version
	private int version;

	@Embedded
	private UserRecorder recorder;

	public MedicalClaim() {

	}

	public String getId() {
		return id;
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

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public MedicalClaimProposal getMedicalClaimProposal() {
		return medicalClaimProposal;
	}

	public void setMedicalClaimProposal(MedicalClaimProposal medicalClaimProposal) {
		this.medicalClaimProposal = medicalClaimProposal;
	}

	public String getClaimRole() {
		return claimRole;
	}

	public void setClaimRole(String claimRole) {
		this.claimRole = claimRole;
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
		result = prime * result + ((claimRole == null) ? 0 : claimRole.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isApproved ? 1231 : 1237);
		result = prime * result + ((medicalClaimProposal == null) ? 0 : medicalClaimProposal.hashCode());
		result = prime * result + ((rejectReason == null) ? 0 : rejectReason.hashCode());
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
		MedicalClaim other = (MedicalClaim) obj;
		if (claimRole == null) {
			if (other.claimRole != null)
				return false;
		} else if (!claimRole.equals(other.claimRole))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isApproved != other.isApproved)
			return false;
		if (medicalClaimProposal == null) {
			if (other.medicalClaimProposal != null)
				return false;
		} else if (!medicalClaimProposal.equals(other.medicalClaimProposal))
			return false;
		if (rejectReason == null) {
			if (other.rejectReason != null)
				return false;
		} else if (!rejectReason.equals(other.rejectReason))
			return false;
		if (version != other.version)
			return false;
		return true;
	}
}
