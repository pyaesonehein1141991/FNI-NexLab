package org.tat.fni.api.dto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.tat.fni.api.common.BeneficiariesInfoDTO;
import org.tat.fni.api.common.InsuredPersonAddOnDTO;
import org.tat.fni.api.common.KeyFactor;
import org.tat.fni.api.common.Name;
import org.tat.fni.api.common.ResidentAddress;
import org.tat.fni.api.common.emumdata.ClaimStatus;
import org.tat.fni.api.common.emumdata.ClassificationOfHealth;
import org.tat.fni.api.common.emumdata.EndorsementStatus;
import org.tat.fni.api.common.emumdata.Gender;
import org.tat.fni.api.common.emumdata.IdType;
import org.tat.fni.api.domain.Attachment;
import org.tat.fni.api.domain.Customer;
import org.tat.fni.api.domain.GradeInfo;
import org.tat.fni.api.domain.InsuredPersonAddon;
import org.tat.fni.api.domain.InsuredPersonAttachment;
import org.tat.fni.api.domain.InsuredPersonBeneficiaries;
import org.tat.fni.api.domain.InsuredPersonKeyFactorValue;
import org.tat.fni.api.domain.Occupation;
import org.tat.fni.api.domain.Product;
import org.tat.fni.api.domain.ProposalInsuredPerson;
import org.tat.fni.api.domain.RelationShip;
import org.tat.fni.api.domain.RiskyOccupation;
import org.tat.fni.api.domain.School;
import org.tat.fni.api.domain.SurveyQuestionAnswer;
import org.tat.fni.api.domain.TypesOfSport;

public class InsuredPersonInfoDTO {
	private int periodMonth;
	private boolean existsEntity;
	private boolean approve;
	private boolean needMedicalCheckup;
	private Boolean isPaidPremiumForPaidup;
	private int age;
	private double premium;
	private double approvedSumInsured;
	private double interest;
	private int weight;
	private double premiumRate;
	private int height;
	private int feets;
	private int inches;
	private int pounds;
	private int dangerousOccupation;
	private double basicTermPremium;
	private double addOnTermPremium;
	private double endorsementAddonPremium;
	private double endorsementBasicPremium;
	private boolean isRiskyOccupation;
	private String phone;
	private Double sumInsuredInfo;
	private String tempId;
	private String fatherName;
	private String insPersonCodeNo;
	private String inPersonGroupCodeNo;
	private String initialId;
	private String rejectReason;
	private String idNo;
	private String provinceCode;
	private String townshipCode;
	private String idConditionType;
	private String fullIdNo;
	private Date dateOfBirth;
	private Product product;
	private Occupation occupation;
	private RiskyOccupation riskyOccupation;
	private Customer customer;
	private Name name;
	private String id;
	private int periodOfYears;
	private Gender gender;
	private IdType idType;
	private TypesOfSport typesOfSport;
	private RelationShip relationship;
	private ResidentAddress residentAddress;
	private ClassificationOfHealth classificationOfHealth;
	private EndorsementStatus endorsementStatus;
	private ClaimStatus claimStatus;
	private List<InsuredPersonAttachment> perAttachmentList;
	private List<InsuredPersonKeyFactorValue> keyFactorValueList;
	private List<BeneficiariesInfoDTO> beneficiariesInfoDTOList;
	private Map<String, InsuredPersonAddOnDTO> insuredPersonAddOnDTOMap = new HashMap<String, InsuredPersonAddOnDTO>();
	private List<InsuredPersonAddOnDTO> insuredPersonAddOnDTOList;
	private List<SurveyQuestionAnswer> surveyQuestionAnswerList;

	private List<Attachment> birthCertificateAttachments;

	private int unit;
	private int approvedUnit;
	private Date startDate;
	private Date endDate;

	private String parentName;
	private Date parentDOB;
	private IdType parentIdType;

	private String parentFullIdNo;
	private String parentIdNo;
	private String parentProvinceCode;
	private String parentTownshipCode;
	private String parentIdConditionType;

	private School school;
	private GradeInfo gradeInfo;

	private int version;

	public boolean isApprove() {
		return approve;
	}

	public void setApprove(boolean approve) {
		this.approve = approve;
	}

	public InsuredPersonInfoDTO() {
		tempId = System.nanoTime() + "";
	}

	public InsuredPersonInfoDTO(ProposalInsuredPerson proposal) {
		if (proposal.getId() == null) {
			this.tempId = System.nanoTime() + "";
		} else {
			existsEntity = true;
			this.approve = proposal.isApproved();
			this.tempId = proposal.getId();
			this.version = proposal.getVersion();
		}

		if (this.isPaidPremiumForPaidup == null) {
			this.isPaidPremiumForPaidup = false;
		}

		this.periodMonth = proposal.getLifeProposal().getPeriodMonth();
		this.startDate = proposal.getLifeProposal().getStartDate();
		this.endDate = proposal.getLifeProposal().getEndDate();
		this.needMedicalCheckup = proposal.isNeedMedicalCheckup();
		this.product = proposal.getProduct();
		this.periodOfYears = proposal.getLifeProposal().getPeriodMonth() / 12;
		this.age = proposal.getAge();
		this.sumInsuredInfo = proposal.getProposedSumInsured();
		this.premium = proposal.getProposedPremium();
		this.approvedSumInsured = proposal.getApprovedSumInsured();
		this.basicTermPremium = proposal.getBasicTermPremium();
		this.addOnTermPremium = proposal.getAddOnTermPremium();
		this.endorsementBasicPremium = proposal.getEndorsementNetBasicPremium();
		this.endorsementAddonPremium = proposal.getEndorsementNetAddonPremium();
		this.interest = proposal.getInterest();
		this.weight = proposal.getWeight();
		this.height = proposal.getHeight();
		this.premiumRate = proposal.getPremiumRate();
		this.feets = proposal.getHeight() / 12;
		this.inches = proposal.getHeight() % 12;
		this.rejectReason = proposal.getRejectReason();
		this.insPersonCodeNo = proposal.getInsPersonCodeNo();
		this.inPersonGroupCodeNo = proposal.getInPersonGroupCodeNo();
		this.initialId = proposal.getInitialId();
		this.idType = proposal.getIdType();
		this.fullIdNo = proposal.getIdNo();
		loadFullIdNo();
		this.fatherName = proposal.getFatherName();
		this.dateOfBirth = proposal.getDateOfBirth();
		this.endorsementStatus = proposal.getEndorsementStatus();
		this.classificationOfHealth = proposal.getClsOfHealth();
		this.gender = proposal.getGender();
		this.residentAddress = proposal.getResidentAddress();
		this.name = proposal.getName();
		this.id = proposal.getId();
		this.typesOfSport = proposal.getTypesOfSport();
		this.occupation = proposal.getOccupation();
		this.customer = proposal.getCustomer();
		this.unit = proposal.getUnit();
		this.approvedUnit = proposal.getApprovedUnit();
		this.relationship = proposal.getRelationship();
		this.surveyQuestionAnswerList = proposal.getSurveyQuestionAnswerList();
		this.phone = proposal.getPhone();
		this.relationship = proposal.getRelationship();
		this.parentDOB = proposal.getParentDOB();
		this.parentFullIdNo = proposal.getParentIdNo();
		this.parentIdNo = proposal.getParentIdNo();
		this.parentIdType = proposal.getParentIdType();
		this.parentName = proposal.getParentName();
		this.school = proposal.getSchool();
		this.gradeInfo = proposal.getGradeInfo();
		for (Attachment attach : proposal.getBirthCertificateAttachment()) {
			addBirthCertificateAttachment(attach);
		}
		for (InsuredPersonAttachment attach : proposal.getAttachmentList()) {
			addInsuredPersonAttachment(attach);
		}
		for (InsuredPersonKeyFactorValue kfv : proposal.getKeyFactorValueList()) {
			addInsuredPersonKeyFactorValue(kfv);
		}
		for (InsuredPersonBeneficiaries beneficiary : proposal.getInsuredPersonBeneficiariesList()) {
			addBeneficiariesInfoDTO(new BeneficiariesInfoDTO(beneficiary));
		}
		if (proposal.getInsuredPersonAddOnList() != null) {
			for (InsuredPersonAddon addOn : proposal.getInsuredPersonAddOnList()) {
				insuredPersonAddOnDTOMap.put(addOn.getId(), new InsuredPersonAddOnDTO(addOn));
			}
		}

	}

	public InsuredPersonInfoDTO(InsuredPersonInfoDTO proposal) {
		if (proposal.getId() == null) {
			this.tempId = proposal.getTempId();
		} else {
			existsEntity = true;
			this.approve = proposal.isApprove();
			this.tempId = proposal.getId();
			this.version = proposal.getVersion();
		}

		if (this.isPaidPremiumForPaidup == null) {
			this.isPaidPremiumForPaidup = false;
		}
		this.periodMonth = proposal.getPeriodMonth();
		this.startDate = proposal.getStartDate();
		this.needMedicalCheckup = proposal.isNeedMedicalCheckup();
		this.product = proposal.getProduct();
		this.age = proposal.getAge();
		this.sumInsuredInfo = proposal.getSumInsuredInfo();
		this.premium = proposal.getPremium();
		this.approvedSumInsured = proposal.getApprovedSumInsured();
		this.basicTermPremium = proposal.getBasicTermPremium();
		this.addOnTermPremium = proposal.getAddOnTermPremium();
		this.endorsementBasicPremium = proposal.getEndorsementNetBasicPremium();
		this.endorsementAddonPremium = proposal.getEndorsementNetAddonPremium();
		this.interest = proposal.getInterest();
		this.weight = proposal.getWeight();
		this.height = proposal.getHeight();
		this.premiumRate = proposal.getPremiumRate();
		this.feets = proposal.getHeight() / 12;
		this.inches = proposal.getHeight() % 12;
		this.rejectReason = proposal.getRejectReason();
		this.insPersonCodeNo = proposal.getInsPersonCodeNo();
		this.inPersonGroupCodeNo = proposal.getInPersonGroupCodeNo();
		this.initialId = proposal.getInitialId();
		this.fullIdNo = proposal.getFullIdNo();
		this.idNo = proposal.getIdNo();
		this.fatherName = proposal.getFatherName();
		this.dateOfBirth = proposal.getDateOfBirth();
		this.endorsementStatus = proposal.getEndorsementStatus();
		this.classificationOfHealth = proposal.getClassificationOfHealth();
		this.gender = proposal.getGender();
		this.idType = proposal.getIdType();
		this.provinceCode = proposal.getProvinceCode();
		this.townshipCode = proposal.getTownshipCode();
		this.idConditionType = proposal.getIdConditionType();
		this.residentAddress = proposal.getResidentAddress();
		this.name = proposal.getName();
		this.id = proposal.getId();
		this.typesOfSport = proposal.getTypesOfSport();
		this.occupation = proposal.getOccupation();
		this.customer = proposal.getCustomer();
		this.unit = proposal.getUnit();
		this.approvedUnit = proposal.getApprovedUnit();
		this.relationship = proposal.getRelationship();
		this.surveyQuestionAnswerList = proposal.getSurveyQuestionAnswerList();
		this.isRiskyOccupation = proposal.getIsRiskyOccupation();
		this.phone = proposal.getPhone();
		this.parentDOB = proposal.getParentDOB();
		this.parentFullIdNo = proposal.getParentFullIdNo();
		this.parentIdNo = proposal.getParentIdNo();
		this.parentIdType = proposal.getParentIdType();
		this.parentName = proposal.getParentName();
		this.school = proposal.getSchool();
		this.gradeInfo = proposal.getGradeInfo();
		for (Attachment attach : proposal.getBirthCertificateAttachments()) {
			addBirthCertificateAttachment(attach);
		}
		for (InsuredPersonAttachment attach : proposal.getPerAttachmentList()) {
			addInsuredPersonAttachment(attach);
		}
		for (InsuredPersonKeyFactorValue kfv : proposal.getKeyFactorValueList()) {
			addInsuredPersonKeyFactorValue(kfv);
		}
		for (BeneficiariesInfoDTO beneficiary : proposal.getBeneficiariesInfoDTOList()) {
			addBeneficiariesInfoDTO(new BeneficiariesInfoDTO(beneficiary));
		}
		if (proposal.getInsuredPersonAddOnDTOList() != null) {
			for (InsuredPersonAddOnDTO addOn : proposal.getInsuredPersonAddOnDTOList()) {
				insuredPersonAddOnDTOMap.put(addOn.getTempId(), new InsuredPersonAddOnDTO(addOn));
			}
		}

	}

	public InsuredPersonInfoDTO(Customer customer) {
		this.initialId = customer.getInitialId();
		this.fatherName = customer.getFatherName();
		this.dateOfBirth = customer.getDateOfBirth();
		this.gender = customer.getGender();
		this.idType = customer.getIdType();
		this.fullIdNo = customer.getFullIdNo();
		loadFullIdNo();
		this.name = customer.getName();
		this.residentAddress = customer.getResidentAddress();
		// this.residentAddress.getTownship() =
		// customer.getResidentAddress().get
		this.occupation = customer.getOccupation();
		this.customer = customer;

	}

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	public Double getSumInsuredInfo() {
		if (sumInsuredInfo == null) {
			sumInsuredInfo = new Double(0.0);
		}
		return sumInsuredInfo;
	}

	public void setSumInsuredInfo(Double sumInsuredInfo) {
		this.sumInsuredInfo = sumInsuredInfo;
	}

	public Number getSumInsuredInfoNum() {
		if (sumInsuredInfo == null) {
			sumInsuredInfo = new Double(0.0);
		}
		return sumInsuredInfo;
	}

	public void setSumInsuredInfoNum(Number sumInsuredInfo) {
		if (sumInsuredInfo != null) {
			this.sumInsuredInfo = sumInsuredInfo.doubleValue();
		}
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
		loadKeyFactor(product);
	}

	private void loadKeyFactor(Product product) {
		keyFactorValueList = new ArrayList<InsuredPersonKeyFactorValue>();
		if (product.getKeyFactorList() != null) {
			for (KeyFactor kf : product.getKeyFactorList()) {
				InsuredPersonKeyFactorValue insKf = new InsuredPersonKeyFactorValue(kf);
				insKf.setKeyFactor(kf);
				keyFactorValueList.add(insKf);
			}
		}
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getInPersonGroupCodeNo() {
		return inPersonGroupCodeNo;
	}

	public void setInPersonGroupCodeNo(String inPersonGroupCodeNo) {
		this.inPersonGroupCodeNo = inPersonGroupCodeNo;
	}

	public List<InsuredPersonAddOnDTO> getInsuredPersonAddOnDTOList() {
		return insuredPersonAddOnDTOList;
	}

	public List<InsuredPersonBeneficiaries> getBeneficiariesInfoList(ProposalInsuredPerson proposalInsuredPerson) {
		List<InsuredPersonBeneficiaries> result = null;
		if (beneficiariesInfoDTOList != null && !beneficiariesInfoDTOList.isEmpty()) {
			result = new ArrayList<InsuredPersonBeneficiaries>();
			for (BeneficiariesInfoDTO beneficiairesInfoDTO : beneficiariesInfoDTOList) {
				InsuredPersonBeneficiaries insPesBenf = new InsuredPersonBeneficiaries(beneficiairesInfoDTO.getBeneficiaryNo(), beneficiairesInfoDTO.getAge(),
						beneficiairesInfoDTO.getPercentage(), beneficiairesInfoDTO.getInitialId(), beneficiairesInfoDTO.getIdNo(), beneficiairesInfoDTO.getGender(),
						beneficiairesInfoDTO.getIdType(), beneficiairesInfoDTO.getResidentAddress(), beneficiairesInfoDTO.getName(), beneficiairesInfoDTO.getRelationship());
				;
				insPesBenf.setProposalInsuredPerson(proposalInsuredPerson);
				result.add(insPesBenf);
			}
		}
		return result;

	}

	public List<InsuredPersonAddon> getInsuredPersonAddOnList(ProposalInsuredPerson proposalInsuredPerson) {
		List<InsuredPersonAddon> result = new ArrayList<InsuredPersonAddon>();
		if (insuredPersonAddOnDTOList != null && !insuredPersonAddOnDTOList.isEmpty()) {
			for (InsuredPersonAddOnDTO insuredPersonAddonDTO : insuredPersonAddOnDTOList) {
				InsuredPersonAddon insAddOn = new InsuredPersonAddon(insuredPersonAddonDTO.getAddOn(), insuredPersonAddonDTO.getAddOnSumInsured());
				insAddOn.setProposalInsuredPerson(proposalInsuredPerson);
				result.add(insAddOn);
			}
		}
		return result;
	}

	public List<InsuredPersonKeyFactorValue> getKeyFactorValueList(ProposalInsuredPerson proposalInsuredPerson) {
		if (proposalInsuredPerson.getKeyFactorValueList() == null || keyFactorValueList == null) {
			return new ArrayList<InsuredPersonKeyFactorValue>();
		} else {
			for (InsuredPersonKeyFactorValue inskf : keyFactorValueList) {
				inskf.setProposalInsuredPerson(proposalInsuredPerson);
			}
			return keyFactorValueList;
		}

	}

	public int getAgeForNextYear() {
		Date date = null;
		Calendar cal_1 = Calendar.getInstance();
		if (startDate != null) {
			cal_1.setTime(startDate);
			date = startDate;
		} else {
			date = new Date();
		}
		int currentYear = cal_1.get(Calendar.YEAR);
		Calendar cal_2 = Calendar.getInstance();
		cal_2.setTime(dateOfBirth);
		cal_2.set(Calendar.YEAR, currentYear);
		if (date.after(cal_2.getTime())) {
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

	public boolean isExistsEntity() {
		return existsEntity;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public double getPremiumRate() {
		return premiumRate;
	}

	public void setPremiumRate(double premiumRate) {
		this.premiumRate = premiumRate;
	}

	public int getFeets() {
		return feets;
	}

	public void setFeets(int feets) {
		this.feets = feets;
	}

	public int getInches() {
		return inches;
	}

	public void setInches(int inches) {
		this.inches = inches;
	}

	public int getPounds() {
		return pounds;
	}

	public int getDangerousOccupation() {
		return dangerousOccupation;
	}

	public void setDangerousOccupation(int dangerousOccupation) {
		this.dangerousOccupation = dangerousOccupation;
	}

	public void setPounds(int pounds) {
		int calPound;
		if (pounds > 0) {
			if (weight == pounds) {
				pounds = weight - pounds;
			} else if (weight > pounds) {
				calPound = pounds * 20 / 100;
				calPound = pounds + calPound;
				if (calPound >= weight) {
					pounds = 0;
				} else {
					pounds = weight - calPound;
				}
			} else {
				calPound = pounds * 15 / 100;
				calPound = pounds - calPound;
				if (calPound <= weight) {
					pounds = 0;
				} else {
					pounds = weight - calPound;
				}
				pounds = calPound - weight;
			}
		}
		pounds = Math.abs(pounds);
		pounds = (int) Math.ceil(pounds);
		this.pounds = pounds;
	}

	public double getEndorsementNetAddonPremium() {
		return endorsementAddonPremium;
	}

	public void setEndorsementNetAddonPremium(double endorsementAddonPremium) {
		this.endorsementAddonPremium = endorsementAddonPremium;
	}

	public double getEndorsementNetBasicPremium() {
		return endorsementBasicPremium;
	}

	public void setEndorsementNetBasicPremium(double endorsementBasicPremium) {
		this.endorsementBasicPremium = endorsementBasicPremium;
	}

	public String getInsPersonCodeNo() {
		return insPersonCodeNo;
	}

	public void setInsPersonCodeNo(String insPersonCodeNo) {
		this.insPersonCodeNo = insPersonCodeNo;
	}

	public EndorsementStatus getEndorsementStatus() {
		return endorsementStatus;
	}

	public void setEndorsementStatus(EndorsementStatus endorsementStatus) {
		this.endorsementStatus = endorsementStatus;
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

	public String getFullAddress() {
		String result = "";
		if (residentAddress.getResidentAddress() != null) {
			if (residentAddress.getResidentAddress() != null && !residentAddress.getResidentAddress().isEmpty()) {
				result = residentAddress.getResidentAddress();
			}
			if (residentAddress.getTownship() != null && !residentAddress.getTownship().getName().isEmpty()) {
				result = result + " " + residentAddress.getTownship().getName();
			}
		}
		return result;
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
		if (idType.equals(IdType.NRCNO) && fullIdNo != null) {
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

	public String setFullIdNo() {
		if (idType.equals(IdType.NRCNO)) {
			fullIdNo = provinceCode + "/" + townshipCode + "(" + idConditionType + ")" + idNo;
		} else if (idType.equals(IdType.FRCNO) || idType.equals(IdType.PASSPORTNO)) {
			fullIdNo = idNo;
		}
		return fullIdNo;
	}

	public Occupation getOccupation() {
		return occupation;
	}

	public void setOccupation(Occupation occupation) {
		this.occupation = occupation;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public ClassificationOfHealth getClassificationOfHealth() {
		return classificationOfHealth;
	}

	public void setClassificationOfHealth(ClassificationOfHealth classificationOfHealth) {
		this.classificationOfHealth = classificationOfHealth;
	}

	public Boolean getIsPaidPremiumForPaidup() {
		return isPaidPremiumForPaidup;
	}

	public void setIsPaidPremiumForPaidup(Boolean isPaidPremiumForPaidup) {
		this.isPaidPremiumForPaidup = isPaidPremiumForPaidup;
	}

	public TypesOfSport getTypesOfSport() {
		return typesOfSport;
	}

	public void setTypesOfSport(TypesOfSport typesOfSport) {
		this.typesOfSport = typesOfSport;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public double getBasicTermPremium() {
		return basicTermPremium;
	}

	public void setBasicTermPremium(double basicTermPremium) {
		this.basicTermPremium = basicTermPremium;
	}

	public double getAddOnTermPremium() {
		return addOnTermPremium;
	}

	public void setAddOnTermPremium(double addOnTermPremium) {
		this.addOnTermPremium = addOnTermPremium;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getApprovedSumInsured() {
		return approvedSumInsured;
	}

	public void setApprovedSumInsured(double approvedSumInsured) {
		this.approvedSumInsured = approvedSumInsured;
	}

	public double getEndorsementAddonPremium() {
		return endorsementAddonPremium;
	}

	public void setEndorsementAddonPremium(double endorsementAddonPremium) {
		this.endorsementAddonPremium = endorsementAddonPremium;
	}

	public double getEndorsementBasicPremium() {
		return endorsementBasicPremium;
	}

	public void setEndorsementBasicPremium(double endorsementBasicPremium) {
		this.endorsementBasicPremium = endorsementBasicPremium;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public ClaimStatus getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(ClaimStatus claimStatus) {
		this.claimStatus = claimStatus;
	}

	public boolean isNeedMedicalCheckup() {
		return needMedicalCheckup;
	}

	public void setNeedMedicalCheckup(boolean needMedicalCheckup) {
		this.needMedicalCheckup = needMedicalCheckup;
	}

	public void addInsuredPersonAddOn(InsuredPersonAddOnDTO insuPersonAddOnDTO) {
		boolean flag = true;
		for (InsuredPersonAddOnDTO fa : insuredPersonAddOnDTOMap.values()) {
			if (insuPersonAddOnDTO.getAddOn().getId().equals(fa.getAddOn().getId())) {
				flag = false;
				break;
			}
		}
		if (flag) {
			insuredPersonAddOnDTOMap.put(insuPersonAddOnDTO.getTempId(), insuPersonAddOnDTO);
		}
	}

	public void removeInsuredPersonAddOn(InsuredPersonAddOnDTO insuPersonAddOnDTO) {
		insuredPersonAddOnDTOList.remove(insuPersonAddOnDTO);
		// insuredPersonAddOnDTOMap.remove(insuPersonAddOnDTO.getTempId());
	}

	public List<InsuredPersonAttachment> getPerAttachmentList() {
		if (perAttachmentList == null) {
			perAttachmentList = new ArrayList<InsuredPersonAttachment>();
		}
		return perAttachmentList;
	}

	public void setPerAttachmentList(List<InsuredPersonAttachment> perAttachmentList) {
		this.perAttachmentList = perAttachmentList;
	}

	public void addInsuredPersonAttachment(InsuredPersonAttachment attach) {
		getPerAttachmentList().add(attach);
	}

	public List<InsuredPersonKeyFactorValue> getKeyFactorValueList() {
		if (keyFactorValueList == null) {
			keyFactorValueList = new ArrayList<InsuredPersonKeyFactorValue>();
		}
		return keyFactorValueList;
	}

	public void setKeyFactorValueList(List<InsuredPersonKeyFactorValue> keyFactorValueList) {
		this.keyFactorValueList = keyFactorValueList;
	}

	public void addInsuredPersonKeyFactorValue(InsuredPersonKeyFactorValue kfv) {
		getKeyFactorValueList().add(kfv);
	}

	public List<BeneficiariesInfoDTO> getBeneficiariesInfoDTOList() {
		if (beneficiariesInfoDTOList == null) {
			beneficiariesInfoDTOList = new ArrayList<BeneficiariesInfoDTO>();
		}
		return beneficiariesInfoDTOList;
	}

	public void setBeneficiariesInfoDTOList(List<BeneficiariesInfoDTO> beneficiariesInfoDTO1List) {
		this.beneficiariesInfoDTOList = beneficiariesInfoDTO1List;
	}

	public void addBeneficiariesInfoDTO(BeneficiariesInfoDTO dto) {
		getBeneficiariesInfoDTOList().add(dto);
	}

	public Map<String, InsuredPersonAddOnDTO> getInsuredPersonAddOnDTOMap() {
		if (insuredPersonAddOnDTOMap == null) {
			insuredPersonAddOnDTOMap = new HashMap<String, InsuredPersonAddOnDTO>();
		}
		return insuredPersonAddOnDTOMap;
	}

	public List<SurveyQuestionAnswer> getSurveyQuestionAnswerList() {
		if (surveyQuestionAnswerList == null) {
			surveyQuestionAnswerList = new ArrayList<SurveyQuestionAnswer>();
		}
		return surveyQuestionAnswerList;
	}

	public void setSurveyQuestionAnswerList(List<SurveyQuestionAnswer> surveyQuestionAnswerList) {
		this.surveyQuestionAnswerList = surveyQuestionAnswerList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public int getApprovedUnit() {
		return approvedUnit;
	}

	public void setApprovedUnit(int approvedUnit) {
		this.approvedUnit = approvedUnit;
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

	public void setInsuredPersonAddOnDTOList(List<InsuredPersonAddOnDTO> insuredPersonAddOnDTOList) {
		this.insuredPersonAddOnDTOList = insuredPersonAddOnDTOList;
	}

	public RelationShip getRelationship() {
		return relationship;
	}

	public void setRelationship(RelationShip relationship) {
		this.relationship = relationship;
	}

	public String getFullIdNo() {
		return fullIdNo;
	}

	public void setFullIdNo(String fullIdNo) {
		this.fullIdNo = fullIdNo;
	}

	public boolean getIsRiskyOccupation() {
		return isRiskyOccupation;
	}

	public void setIsRiskyOccupation(boolean isRiskyOccupation) {
		this.isRiskyOccupation = isRiskyOccupation;
	}

	public RiskyOccupation getRiskyOccupation() {
		return riskyOccupation;
	}

	public void setRiskyOccupation(RiskyOccupation riskyOccupation) {
		this.riskyOccupation = riskyOccupation;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}

	public List<Attachment> getBirthCertificateAttachments() {
		if (birthCertificateAttachments == null) {
			birthCertificateAttachments = new ArrayList<Attachment>();
		}
		return birthCertificateAttachments;
	}

	public void setBirthCertificateAttachments(List<Attachment> birthCertificateAttachments) {
		this.birthCertificateAttachments = birthCertificateAttachments;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Date getParentDOB() {
		return parentDOB;
	}

	public void setParentDOB(Date parentDOB) {
		this.parentDOB = parentDOB;
	}

	public IdType getParentIdType() {
		return parentIdType;
	}

	public void setParentIdType(IdType parentIdType) {
		this.parentIdType = parentIdType;
	}

	public String getParentFullIdNo() {
		return parentFullIdNo;
	}

	public void setParentFullIdNo(String parentFullIdNo) {
		this.parentFullIdNo = parentFullIdNo;
	}

	public String getParentIdNo() {
		return parentIdNo;
	}

	public void setParentIdNo(String parentIdNo) {
		this.parentIdNo = parentIdNo;
	}

	public String getParentProvinceCode() {
		return parentProvinceCode;
	}

	public void setParentProvinceCode(String parentProvinceCode) {
		this.parentProvinceCode = parentProvinceCode;
	}

	public String getParentTownshipCode() {
		return parentTownshipCode;
	}

	public void setParentTownshipCode(String parentTownshipCode) {
		this.parentTownshipCode = parentTownshipCode;
	}

	public String getParentIdConditionType() {
		return parentIdConditionType;
	}

	public void setParentIdConditionType(String parentIdConditionType) {
		this.parentIdConditionType = parentIdConditionType;
	}

	public String setParentFullIdNo() {
		if (parentIdType.equals(IdType.NRCNO)) {
			parentFullIdNo = parentProvinceCode + "/" + parentTownshipCode + "(" + parentIdConditionType + ")" + parentIdNo;
		} else if (parentIdType.equals(IdType.FRCNO) || parentIdType.equals(IdType.PASSPORTNO)) {
			parentFullIdNo = parentIdNo;
		}
		return parentFullIdNo;
	}

	public void loadParentFullIdNo() {
		if (parentIdType.equals(IdType.NRCNO) && parentFullIdNo != null && !parentFullIdNo.isEmpty()) {
			StringTokenizer token = new StringTokenizer(parentFullIdNo, "/()");
			parentProvinceCode = token.nextToken();
			parentTownshipCode = token.nextToken();
			parentIdConditionType = token.nextToken();
			parentIdNo = token.nextToken();
			parentFullIdNo = parentProvinceCode.equals("null") ? "" : parentFullIdNo;
		} else if (parentIdType.equals(IdType.FRCNO) || parentIdType.equals(IdType.PASSPORTNO)) {
			parentIdNo = parentFullIdNo == null ? "" : parentFullIdNo;
		}
	}

	public int getPeriodOfYears() {
		return Math.abs(periodOfYears);
	}

	public void setPeriodOfYears(int periodOfYears) {
		this.periodOfYears = periodOfYears;
	}

	public int getPeriodOfMonths() {
		return periodOfYears * 12;
	}

	public void addBirthCertificateAttachment(Attachment attach) {
		if (birthCertificateAttachments == null) {
			birthCertificateAttachments = new ArrayList<Attachment>();
		}
		birthCertificateAttachments.add(attach);
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public GradeInfo getGradeInfo() {
		return gradeInfo;
	}

	public void setGradeInfo(GradeInfo gradeInfo) {
		this.gradeInfo = gradeInfo;
	}

	public int getPeriodMonth() {
		return periodMonth;
	}

	public void setPeriodMonth(int periodMonth) {
		this.periodMonth = periodMonth;
	}

	public String getPeriod() {
		periodMonth = getPeriodOfMonths();
		if (periodMonth / 12 < 1) {
			return periodMonth + " - Months";
		} else {
			return periodMonth / 12 + " - Year";
		}
	}

	public int getPremiumTerm() {
		return periodOfYears - 3;
	}
}
