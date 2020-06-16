package org.tat.fni.api.domain.lifepolicy;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.tat.fni.api.common.BeneficiariesInfoDTO;
import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.Name;
import org.tat.fni.api.common.ResidentAddress;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;
import org.tat.fni.api.common.emumdata.ClaimStatus;
import org.tat.fni.api.common.emumdata.Gender;
import org.tat.fni.api.common.emumdata.IdType;
import org.tat.fni.api.common.utils.Utils;
import org.tat.fni.api.domain.InsuredPersonBeneficiaries;
import org.tat.fni.api.domain.RelationShip;


@Entity
@Table(name = TableName.LIFEPOLICYINSUREDPERSONBENEFICIARIES)
@TableGenerator(name = "LPOLINSUREDPERSONBENEFICIARIES_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LPOLINSUREDPERSONBENEFICIARIES_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "PolicyInsuredPersonBeneficiaries.findAll", query = "SELECT p FROM PolicyInsuredPersonBeneficiaries p "),
		@NamedQuery(name = "PolicyInsuredPersonBeneficiaries.findById", query = "SELECT p FROM PolicyInsuredPersonBeneficiaries p WHERE p.id = :id"),
		@NamedQuery(name = "PolicyInsuredPersonBeneficiaries.updateClaimStatus", query = "UPDATE PolicyInsuredPersonBeneficiaries p SET p.claimStatus = :claimStatus WHERE p.id = :id") })
@EntityListeners(IDInterceptor.class)
public class PolicyInsuredPersonBeneficiaries implements Serializable {
	private static final long serialVersionUID = 7644853693620015430L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LPOLINSUREDPERSONBENEFICIARIES_GEN")
	private String id;

	private int age;
	private float percentage;
	private String beneficiaryNo;
	private String initialId;
	private String idNo;
	private String phone;

	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	@Enumerated(value = EnumType.STRING)
	private Gender gender;

	@Enumerated(value = EnumType.STRING)
	private IdType idType;

	@Enumerated(EnumType.STRING)  
	private ClaimStatus claimStatus;

	@Embedded
	private ResidentAddress residentAddress;

	@Embedded
	private Name name;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RELATIONSHIPID", referencedColumnName = "ID")
	private RelationShip relationship;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INSUREDPERSONID", referencedColumnName = "ID")
	private PolicyInsuredPerson policyInsuredPerson;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public PolicyInsuredPersonBeneficiaries() {

	}

	public PolicyInsuredPersonBeneficiaries(InsuredPersonBeneficiaries insuredPersonBeneficiaries) {
		this.beneficiaryNo = insuredPersonBeneficiaries.getBeneficiaryNo();
		this.dateOfBirth = insuredPersonBeneficiaries.getDateOfBirth();
		this.age = insuredPersonBeneficiaries.getAge();
		this.percentage = insuredPersonBeneficiaries.getPercentage();
		this.relationship = insuredPersonBeneficiaries.getRelationship();
		this.initialId = insuredPersonBeneficiaries.getInitialId();
		this.idNo = insuredPersonBeneficiaries.getIdNo();
		this.gender = insuredPersonBeneficiaries.getGender();
		this.idType = insuredPersonBeneficiaries.getIdType();
		this.residentAddress = insuredPersonBeneficiaries.getResidentAddress();
		this.name = insuredPersonBeneficiaries.getName();
		this.relationship = insuredPersonBeneficiaries.getRelationship();
		this.phone = insuredPersonBeneficiaries.getPhone();
	}

//	public PolicyInsuredPersonBeneficiaries(PolicyInsuredPersonBeneficiariesHistory insuredPersonBeneficiaries) {
//		this.beneficiaryNo = insuredPersonBeneficiaries.getBeneficiaryNo();
//		this.dateOfBirth = insuredPersonBeneficiaries.getDateOfBirth();
//		this.age = insuredPersonBeneficiaries.getAge();
//		this.percentage = insuredPersonBeneficiaries.getPercentage();
//		this.relationship = insuredPersonBeneficiaries.getRelationship();
//		this.initialId = insuredPersonBeneficiaries.getInitialId();
//		this.idNo = insuredPersonBeneficiaries.getIdNo();
//		this.gender = insuredPersonBeneficiaries.getGender();
//		this.idType = insuredPersonBeneficiaries.getIdType();
//		this.residentAddress = insuredPersonBeneficiaries.getResidentAddress();
//		this.name = insuredPersonBeneficiaries.getName();
//		this.relationship = insuredPersonBeneficiaries.getRelationship();
//		this.claimStatus = insuredPersonBeneficiaries.getClaimStatus();
//		this.phone = insuredPersonBeneficiaries.getPhone();
//	}

	public PolicyInsuredPersonBeneficiaries(BeneficiariesInfoDTO dto) {
		// this.id = beneficiariesInfoDTO.getTempId();
		this.beneficiaryNo = dto.getBeneficiaryNo();
		this.dateOfBirth = dto.getDateOfBirth();
		this.age = dto.getAge();
		this.percentage = dto.getPercentage();
		this.initialId = dto.getInitialId();
		this.idNo = dto.getIdNo();
		this.gender = dto.getGender();
		this.idType = dto.getIdType();
		this.residentAddress = dto.getResidentAddress();
		this.name = dto.getName();
		this.relationship = dto.getRelationship();
		// this.version = beneficiariesInfoDTO.getVersion();
		this.phone = dto.getPhone();
		if (dto.isExistEntity()) {
			this.id = dto.getTempId();
			this.version = dto.getVersion();
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void overwriteId(String id) {
		this.id = id;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public String getBeneficiaryNo() {
		return beneficiaryNo;
	}

	public void setBeneficiaryNo(String beneficiaryNo) {
		this.beneficiaryNo = beneficiaryNo;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public float getPercentage() {
		return percentage;
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}

	public RelationShip getRelationship() {
		return relationship;
	}

	public String getRelationshipName() {
		if (relationship != null)
			return relationship.getName();
		else
			return "";
	}

	public void setRelationship(RelationShip relationship) {
		this.relationship = relationship;
	}

	public PolicyInsuredPerson getPolicyInsuredPerson() {
		return policyInsuredPerson;
	}

	public void setPolicyInsuredPerson(PolicyInsuredPerson policyInsuredPerson) {
		this.policyInsuredPerson = policyInsuredPerson;
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

	public String getIdNoForView() {
		if (idNo == null || idNo.isEmpty())
			return "Still Applying";
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
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
		return residentAddress;
	}

	public void setResidentAddress(ResidentAddress residentAddress) {
		this.residentAddress = residentAddress;
	}

	public ClaimStatus getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(ClaimStatus claimStatus) {
		this.claimStatus = claimStatus;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getPhone() {
		return phone;
	}

	public String getPhoneForView() {
		if (phone != null && !phone.isEmpty())
			return phone;
		else
			return "-";
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public String getDateOfBirthForView() {
		if (dateOfBirth == null)
			return "-";
		return Utils.formattedDate(dateOfBirth);
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getFullName() {
		String result = "";
		if (name != null) {
			if (initialId != null && !initialId.isEmpty()) {
				result = initialId.trim();
			}
			if (name.getFirstName() != null && !name.getFirstName().isEmpty()) {
				result = result + " " + name.getFirstName();
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

	public String getFullAddress() {
		String result = "";
		if (residentAddress != null) {
			if (residentAddress.getResidentAddress() != null && !residentAddress.getResidentAddress().isEmpty()) {
				result = result + residentAddress.getResidentAddress();
			}
			if (residentAddress.getTownship() != null && !residentAddress.getTownship().getFullTownShip().isEmpty()) {
				result = result + ", " + residentAddress.getTownship().getFullTownShip();
			}
		}
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((beneficiaryNo == null) ? 0 : beneficiaryNo.hashCode());
		result = prime * result + ((claimStatus == null) ? 0 : claimStatus.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idNo == null) ? 0 : idNo.hashCode());
		result = prime * result + ((idType == null) ? 0 : idType.hashCode());
		result = prime * result + ((initialId == null) ? 0 : initialId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Float.floatToIntBits(percentage);
		result = prime * result + ((policyInsuredPerson == null) ? 0 : policyInsuredPerson.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((relationship == null) ? 0 : relationship.hashCode());
		result = prime * result + ((residentAddress == null) ? 0 : residentAddress.hashCode());
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
		PolicyInsuredPersonBeneficiaries other = (PolicyInsuredPersonBeneficiaries) obj;
		if (age != other.age)
			return false;
		if (beneficiaryNo == null) {
			if (other.beneficiaryNo != null)
				return false;
		} else if (!beneficiaryNo.equals(other.beneficiaryNo))
			return false;
		if (claimStatus != other.claimStatus)
			return false;
		if (gender != other.gender)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		if (policyInsuredPerson == null) {
			if (other.policyInsuredPerson != null)
				return false;
		} else if (!policyInsuredPerson.equals(other.policyInsuredPerson))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
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
		if (version != other.version)
			return false;
		return true;
	}
}
