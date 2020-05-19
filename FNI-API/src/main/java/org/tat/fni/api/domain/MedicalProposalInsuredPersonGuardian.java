package org.tat.fni.api.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;



@Entity
@Table(name = TableName.GUARDIAN)
@TableGenerator(name = "GUARDIAN_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "GUARDIAN_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class MedicalProposalInsuredPersonGuardian {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "GUARDIAN_GEN")
	private String id;
	private String guardianNo;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMERID", referencedColumnName = "ID")
	private Customer customer;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RELATIONSHIPID", referencedColumnName = "ID")
	private RelationShip relationship;
	@Transient
	private boolean isSameCustomer;
	@Version
	private int version;
	@Embedded
	private UserRecorder recorder;

	public MedicalProposalInsuredPersonGuardian() {
	}

//	public MedicalProposalInsuredPersonGuardian(MedicalPolicyInsuredPersonGuardian policyGuardian) {
//		this.guardianNo = policyGuardian.getGuardianNo();
//		this.customer = policyGuardian.getCustomer();
//		this.relationship = policyGuardian.getRelationship();
//	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGuardianNo() {
		return guardianNo;
	}

	public void setGuardianNo(String guardianNo) {
		this.guardianNo = guardianNo;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public RelationShip getRelationship() {
		return relationship;
	}

	public void setRelationship(RelationShip relationship) {
		this.relationship = relationship;
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

	public boolean isSameCustomer() {
		return isSameCustomer;
	}

	public void setSameCustomer(boolean isSameCustomer) {
		this.isSameCustomer = isSameCustomer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((guardianNo == null) ? 0 : guardianNo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isSameCustomer ? 1231 : 1237);
		result = prime * result + ((relationship == null) ? 0 : relationship.hashCode());
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
		MedicalProposalInsuredPersonGuardian other = (MedicalProposalInsuredPersonGuardian) obj;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (guardianNo == null) {
			if (other.guardianNo != null)
				return false;
		} else if (!guardianNo.equals(other.guardianNo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isSameCustomer != other.isSameCustomer)
			return false;
		if (relationship == null) {
			if (other.relationship != null)
				return false;
		} else if (!relationship.equals(other.relationship))
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
