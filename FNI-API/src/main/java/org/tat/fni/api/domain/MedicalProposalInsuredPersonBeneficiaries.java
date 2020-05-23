package org.tat.fni.api.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.Name;
import org.tat.fni.api.common.ResidentAddress;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;
import org.tat.fni.api.common.emumdata.ContentInfo;
import org.tat.fni.api.common.emumdata.Gender;
import org.tat.fni.api.common.emumdata.IdType;



@Entity
@Table(name = TableName.MEDICALPROPOSALINSUREDPERSONBENEFICIARIES)
@TableGenerator(name = "MPINSUREDPERSONBENEFICIARIES_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "MPINSUREDPERSONBENEFICIARIES_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class MedicalProposalInsuredPersonBeneficiaries {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "MPINSUREDPERSONBENEFICIARIES_GEN")
	private String id;

	private String beneficiaryNo;
	private String initialId;
	private String fatherName;
	private String fullIdNo;

	private float percentage;

	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	@Enumerated(value = EnumType.STRING)
	private Gender gender;

	@Enumerated(value = EnumType.STRING)
	private IdType idType;

	@Embedded
	private Name name;

	@Embedded
	private ContentInfo contentInfo;

	@Embedded
	private ResidentAddress residentAddress;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RELATIONSHIPID", referencedColumnName = "ID")
	private RelationShip relationship;

	@Transient
	private String stateCode;
	@Transient
	private String townshipCode;
	@Transient
	private String idConditionType;
	@Transient
	private String idNo;

	@Embedded
	private UserRecorder recorder;

	@Version
	private int version;

	public MedicalProposalInsuredPersonBeneficiaries() {
	}

	public MedicalProposalInsuredPersonBeneficiaries(MedicalProposalInsuredPersonBeneficiaries beneficiary) {
		this.id = beneficiary.getId();
		this.dateOfBirth = beneficiary.getDateOfBirth();
		this.percentage = beneficiary.getPercentage();
		this.beneficiaryNo = beneficiary.getBeneficiaryNo();
		this.initialId = beneficiary.getInitialId();
		this.fatherName = beneficiary.getFatherName();
		this.idNo = beneficiary.getIdNo();
		this.gender = beneficiary.getGender();
		this.idType = beneficiary.getIdType();
		this.residentAddress = beneficiary.getResidentAddress();
		this.contentInfo = beneficiary.getContentInfo();
		this.name = beneficiary.getName();
		this.relationship = beneficiary.getRelationship();
		this.version = beneficiary.getVersion();
	}

//	public MedicalProposalInsuredPersonBeneficiaries(MedicalPolicyInsuredPersonBeneficiaries policyBeneficiary) {
//		this.id = policyBeneficiary.getId();
//		this.dateOfBirth = policyBeneficiary.getDateOfBirth();
//		this.percentage = policyBeneficiary.getPercentage();
//		this.beneficiaryNo = policyBeneficiary.getBeneficiaryNo();
//		this.initialId = policyBeneficiary.getInitialId();
//		this.fatherName = policyBeneficiary.getFatherName();
//		this.idNo = policyBeneficiary.getIdNo();
//		this.gender = policyBeneficiary.getGender();
//		this.idType = policyBeneficiary.getIdType();
//		this.residentAddress = policyBeneficiary.getResidentAddress();
//		this.contentInfo = policyBeneficiary.getContentInfo();
//		this.name = policyBeneficiary.getName();
//		this.relationship = policyBeneficiary.getRelationship();
//		this.version = policyBeneficiary.getVersion();
//	}

	public String getFullIdNo() {
		return fullIdNo;
	}

	public void setFullIdNo(String fullIdNo) {
		this.fullIdNo = fullIdNo;
	}

	public String getId() {
		return id;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getTownshipCode() {
		return townshipCode;
	}

	public void setTownshipCode(String townshipCode) {
		this.townshipCode = townshipCode;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public float getPercentage() {
		return percentage;
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}

	public String getBeneficiaryNo() {
		return beneficiaryNo;
	}

	public void setBeneficiaryNo(String beneficiaryNo) {
		this.beneficiaryNo = beneficiaryNo;
	}

	public String getInitialId() {
		return initialId;
	}

	public void setInitialId(String initialId) {
		this.initialId = initialId;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getIdConditionType() {
		return idConditionType;
	}

	public void setIdConditionType(String idConditionType) {
		this.idConditionType = idConditionType;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public IdType getIdType() {
		return idType;
	}

	public void setIdType(IdType idType) {
		this.idType = idType;
	}

	public ResidentAddress getResidentAddress() {
		if (residentAddress == null) {
			residentAddress = new ResidentAddress();
		}
		return residentAddress;
	}

	public void setResidentAddress(ResidentAddress residentAddress) {
		this.residentAddress = residentAddress;
	}

	public Name getName() {
		if (name == null) {
			name = new Name();
		}
		return name;
	}

	public void setName(Name name) {
		this.name = name;
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

	public boolean isValidBeneficiaries() {
		if (percentage <= 0) {
			return false;
		}
		if (getAgeForNextYear() < 0) {
			return false;
		}
		return true;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public ContentInfo getContentInfo() {
		if (this.contentInfo == null) {
			this.contentInfo = new ContentInfo();
		}
		return this.contentInfo;
	}

	public void setContentInfo(ContentInfo contentInfo) {
		this.contentInfo = contentInfo;
	}

	/*************************
	 * System Generated Method
	 *************************/
	public int getAgeForNextYear() {
		Calendar cal_1 = Calendar.getInstance();
		int currentYear = cal_1.get(Calendar.YEAR);
		Calendar cal_2 = Calendar.getInstance();
		cal_2.setTime(dateOfBirth);
		cal_2.set(Calendar.YEAR, currentYear);
		if (new Date().after(cal_2.getTime())) {
			Calendar cal_3 = Calendar.getInstance();
			cal_3.setTime(dateOfBirth);
			int year_1 = cal_3.get(Calendar.YEAR);
			int year_2 = cal_1.get(Calendar.YEAR) + 1;
			return year_2 - year_1;
		} else {
			Calendar cal_3 = Calendar.getInstance();
			cal_3.setTime(dateOfBirth);
			int year_1 = cal_3.get(Calendar.YEAR);
			int year_2 = cal_1.get(Calendar.YEAR);
			return year_2 - year_1;
		}
	}

	public void setFullIdNo() {
		if (idType.equals(IdType.NRCNO)) {
			fullIdNo = stateCode + "/" + townshipCode + "(" + idConditionType + ")" + idNo;
		} else if (idType.equals(IdType.FRCNO) || idType.equals(IdType.PASSPORTNO)) {
			fullIdNo = idNo;
		}
	}

	public void loadTransientIdNo() {
		if (idType.equals(IdType.NRCNO) && fullIdNo != null) {
			StringTokenizer token = new StringTokenizer(fullIdNo, "/()");
			stateCode = token.nextToken();
			townshipCode = token.nextToken();
			idConditionType = token.nextToken();
			idNo = token.nextToken();
			fullIdNo = stateCode.equals("null") ? "" : fullIdNo;
		} else if (idType.equals(IdType.FRCNO) || idType.equals(IdType.PASSPORTNO)) {
			idNo = fullIdNo == null ? "" : fullIdNo;
		}
	}

	public String getFullName() {
		String result = "";
		if (name != null) {
			if (initialId != null && !initialId.isEmpty()) {
				result = initialId;
			}
			if (name.getFirstName() != null && !name.getFirstName().isEmpty()) {
				result = result.trim() + " " + name.getFirstName();
			}
			if (name.getMiddleName() != null && !name.getMiddleName().isEmpty()) {
				result = result + " " + name.getMiddleName();
			}
			if (name.getLastName() != null && !name.getLastName().isEmpty()) {
				result = result + " " + name.getLastName();
			}
		}
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((beneficiaryNo == null) ? 0 : beneficiaryNo.hashCode());
		result = prime * result + ((contentInfo == null) ? 0 : contentInfo.hashCode());
		result = prime * result + ((fatherName == null) ? 0 : fatherName.hashCode());
		result = prime * result + ((fullIdNo == null) ? 0 : fullIdNo.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idConditionType == null) ? 0 : idConditionType.hashCode());
		result = prime * result + ((idNo == null) ? 0 : idNo.hashCode());
		result = prime * result + ((idType == null) ? 0 : idType.hashCode());
		result = prime * result + ((initialId == null) ? 0 : initialId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Float.floatToIntBits(percentage);
		result = prime * result + ((relationship == null) ? 0 : relationship.hashCode());
		result = prime * result + ((residentAddress == null) ? 0 : residentAddress.hashCode());
		result = prime * result + ((stateCode == null) ? 0 : stateCode.hashCode());
		result = prime * result + ((townshipCode == null) ? 0 : townshipCode.hashCode());
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
		MedicalProposalInsuredPersonBeneficiaries other = (MedicalProposalInsuredPersonBeneficiaries) obj;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (beneficiaryNo == null) {
			if (other.beneficiaryNo != null)
				return false;
		} else if (!beneficiaryNo.equals(other.beneficiaryNo))
			return false;
		if (contentInfo == null) {
			if (other.contentInfo != null)
				return false;
		} else if (!contentInfo.equals(other.contentInfo))
			return false;
		if (fatherName == null) {
			if (other.fatherName != null)
				return false;
		} else if (!fatherName.equals(other.fatherName))
			return false;
		if (fullIdNo == null) {
			if (other.fullIdNo != null)
				return false;
		} else if (!fullIdNo.equals(other.fullIdNo))
			return false;
		if (gender != other.gender)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idConditionType != other.idConditionType)
			return false;
		if (idNo == null) {
			if (other.idNo != null)
				return false;
		} else if (!idNo.equals(other.idNo))
			return false;
		if (idType != other.idType)
			return false;
		if (initialId == null) {
			if (other.initialId != null)
				return false;
		} else if (!initialId.equals(other.initialId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Float.floatToIntBits(percentage) != Float.floatToIntBits(other.percentage))
			return false;
		if (relationship == null) {
			if (other.relationship != null)
				return false;
		} else if (!relationship.equals(other.relationship))
			return false;
		if (residentAddress == null) {
			if (other.residentAddress != null)
				return false;
		} else if (!residentAddress.equals(other.residentAddress))
			return false;
		if (stateCode == null) {
			if (other.stateCode != null)
				return false;
		} else if (!stateCode.equals(other.stateCode))
			return false;
		if (townshipCode == null) {
			if (other.townshipCode != null)
				return false;
		} else if (!townshipCode.equals(other.townshipCode))
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
