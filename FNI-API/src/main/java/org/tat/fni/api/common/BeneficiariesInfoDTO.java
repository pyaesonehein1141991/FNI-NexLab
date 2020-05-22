package org.tat.fni.api.common;

import java.util.Date;
import java.util.StringTokenizer;

import org.tat.fni.api.common.emumdata.Gender;
import org.tat.fni.api.common.emumdata.IdType;
import org.tat.fni.api.domain.InsuredPersonBeneficiaries;
import org.tat.fni.api.domain.RelationShip;



public class BeneficiariesInfoDTO {
	private boolean existEntity;
	private int age;
	private float percentage;
	private String tempId;
	private String beneficiaryNo;
	private String phone;

	private RelationShip relationship;
	private String initialId;
	private Gender gender;
	private IdType idType;
	private ResidentAddress residentAddress;

	private Date dateOfBirth;
	private Name name;
	private String idNo;
	private String provinceCode;
	private String townshipCode;
	private String idConditionType;
	private String fullIdNo;
	private int version;

	public BeneficiariesInfoDTO() {
		tempId = System.nanoTime() + "";
	}

	public BeneficiariesInfoDTO(InsuredPersonBeneficiaries beneficiary) {
		if (beneficiary.getId() == null) {
			this.tempId = System.nanoTime() + "";
		} else {
			existEntity = true;
			this.tempId = beneficiary.getId();
			this.version = beneficiary.getVersion();
		}
		this.dateOfBirth = beneficiary.getDateOfBirth();
		this.age = beneficiary.getAge();
		this.percentage = beneficiary.getPercentage();
		this.beneficiaryNo = beneficiary.getBeneficiaryNo();
		this.initialId = beneficiary.getInitialId();
		this.idType = beneficiary.getIdType();
		this.fullIdNo = beneficiary.getIdNo();
		loadFullIdNo();
		this.gender = beneficiary.getGender();
		this.residentAddress = beneficiary.getResidentAddress();
		this.name = beneficiary.getName();
		this.relationship = beneficiary.getRelationship();
		this.phone = beneficiary.getPhone();
	}

	//commented methods related to policy 
//	public BeneficiariesInfoDTO(PolicyInsuredPersonBeneficiaries policyInsuredPersonBeneficiaries) {
//		if (policyInsuredPersonBeneficiaries.getId() == null) {
//			this.tempId = System.nanoTime() + "";
//		} else {
//			this.tempId = policyInsuredPersonBeneficiaries.getId();
//			this.version = policyInsuredPersonBeneficiaries.getVersion();
//		}
//		this.age = policyInsuredPersonBeneficiaries.getAge();
//		this.percentage = policyInsuredPersonBeneficiaries.getPercentage();
//		this.beneficiaryNo = policyInsuredPersonBeneficiaries.getBeneficiaryNo();
//		this.initialId = policyInsuredPersonBeneficiaries.getInitialId();
//		this.gender = policyInsuredPersonBeneficiaries.getGender();
//		this.idType = policyInsuredPersonBeneficiaries.getIdType();
//		this.fullIdNo = policyInsuredPersonBeneficiaries.getIdNo();
//		loadFullIdNo();
//		this.idNo = policyInsuredPersonBeneficiaries.getIdNo();
//		this.residentAddress = policyInsuredPersonBeneficiaries.getResidentAddress();
//		this.name = policyInsuredPersonBeneficiaries.getName();
//		this.relationship = policyInsuredPersonBeneficiaries.getRelationship();
//		this.phone = policyInsuredPersonBeneficiaries.getPhone();
//	}

	public BeneficiariesInfoDTO(BeneficiariesInfoDTO beneficiary) {
		this.tempId = beneficiary.getTempId();
		this.existEntity = beneficiary.isExistEntity();
		this.version = beneficiary.getVersion();
		this.age = beneficiary.getAge();
		this.dateOfBirth = beneficiary.getDateOfBirth();
		this.percentage = beneficiary.getPercentage();
		this.beneficiaryNo = beneficiary.getBeneficiaryNo();
		this.initialId = beneficiary.getInitialId();
		this.fullIdNo = beneficiary.getFullIdNo();
		this.gender = beneficiary.getGender();
		this.idType = beneficiary.getIdType();
		this.residentAddress = beneficiary.getResidentAddress();
		this.name = beneficiary.getName();
		this.relationship = beneficiary.getRelationship();
		this.idConditionType = beneficiary.getIdConditionType();
		this.provinceCode = beneficiary.getProvinceCode();
		this.townshipCode = beneficiary.getTownshipCode();
		this.idNo = beneficiary.getIdNo();
		this.phone = beneficiary.getPhone();
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

	/*
	 * public void setCustomer(Customer customer) { this.customer = customer;
	 * if(customer != null) { Date dateOfBirth = customer.getDateOfBirth();
	 * Calendar cal_1 = Calendar.getInstance(); int currentYear =
	 * cal_1.get(Calendar.YEAR); Calendar cal_2 = Calendar.getInstance();
	 * cal_2.setTime(dateOfBirth); cal_2.set(Calendar.YEAR, currentYear); if(new
	 * Date().after(cal_2.getTime())) { Calendar cal_3 = Calendar.getInstance();
	 * cal_3.setTime(dateOfBirth); int year_1 = cal_3.get(Calendar.YEAR); int
	 * year_2 = cal_1.get(Calendar.YEAR) + 1; age = year_2 - year_1; } else {
	 * Calendar cal_3 = Calendar.getInstance(); cal_3.setTime(dateOfBirth); int
	 * year_1 = cal_3.get(Calendar.YEAR); int year_2 = cal_1.get(Calendar.YEAR);
	 * age = year_2 - year_1; } } }
	 */
	public RelationShip getRelationship() {
		return relationship;
	}

	public void setRelationship(RelationShip relationship) {
		this.relationship = relationship;
	}

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	public boolean isValidBeneficiaries() {
		/*
		 * if(customer == null) { return false; }
		 */
		if (percentage <= 0) {
			return false;
		}
		if (age < 0) {
			return false;
		}
		return true;
	}

	public String getInitialId() {
		return initialId;
	}

	public void setInitialId(String initialId) {
		this.initialId = initialId;
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

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFullName() {
		String result = "";
		if (name != null) {
			if (initialId != null && !initialId.isEmpty()) {
				result = initialId;
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

	public void loadFullIdNo() {
		if (idType.equals(IdType.NRCNO) && fullIdNo != null && !fullIdNo.isEmpty()) {
			StringTokenizer token = new StringTokenizer(fullIdNo, "/()");
			provinceCode = token.nextToken();
			townshipCode = token.nextToken();
			idConditionType = token.nextToken();
			idNo = token.nextToken();
			fullIdNo = provinceCode.equals("null") ? "" : fullIdNo;
		} else if (idType.equals(IdType.FRCNO) || idType.equals(IdType.PASSPORTNO)) {
			idNo = fullIdNo == null ? "" : fullIdNo;
		}
	}

	public String setBenefFullIdNo() {
		if (idType.equals(IdType.NRCNO)) {
			fullIdNo = provinceCode + "/" + townshipCode + "(" + idConditionType + ")" + idNo;
		} else if (idType.equals(IdType.FRCNO) || idType.equals(IdType.PASSPORTNO)) {
			fullIdNo = idNo;
		}
		return fullIdNo;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public boolean isExistEntity() {
		return existEntity;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getTownshipCode() {
		return townshipCode;
	}

	public void setTownshipCode(String townshipCode) {
		this.townshipCode = townshipCode;
	}

	public String getIdConditionType() {
		return idConditionType;
	}

	public void setIdConditionType(String idConditionType) {
		this.idConditionType = idConditionType;
	}

	public String getFullIdNo() {
		return fullIdNo;
	}

	public void setFullIdNo(String fullIdNo) {
		this.fullIdNo = fullIdNo;
	}

	public void setExistEntity(boolean existEntity) {
		this.existEntity = existEntity;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
}
