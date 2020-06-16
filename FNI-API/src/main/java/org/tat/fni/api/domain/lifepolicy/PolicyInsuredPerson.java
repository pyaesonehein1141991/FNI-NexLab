package org.tat.fni.api.domain.lifepolicy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.tat.fni.api.common.BeneficiariesInfoDTO;
import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.InsuredPersonAddOnDTO;
import org.tat.fni.api.common.InsuredPersonInfoDTO;
import org.tat.fni.api.common.Name;
import org.tat.fni.api.common.ResidentAddress;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;
import org.tat.fni.api.common.emumdata.ClaimStatus;
import org.tat.fni.api.common.emumdata.ClassificationOfHealth;
import org.tat.fni.api.common.emumdata.EndorsementStatus;
import org.tat.fni.api.common.emumdata.Gender;
import org.tat.fni.api.common.emumdata.IdType;
import org.tat.fni.api.common.interfaces.IInsuredItem;
import org.tat.fni.api.common.utils.Utils;
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
import org.tat.fni.api.domain.TypesOfSport;


@Entity
@Table(name = TableName.LIFEPOLICYINSUREDPERSON)
@TableGenerator(name = "LPOLINSURPERSON_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LPOLINSURPERSON_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "PolicyInsuredPerson.findAll", query = "SELECT s FROM PolicyInsuredPerson s "),
		@NamedQuery(name = "PolicyInsuredPerson.findById", query = "SELECT s FROM PolicyInsuredPerson s WHERE s.id = :id"),
		@NamedQuery(name = "PolicyInsuredPerson.updateClaimStatus", query = "UPDATE PolicyInsuredPerson p SET p.claimStatus = :claimStatus WHERE p.id = :id") })
@EntityListeners(IDInterceptor.class)
public class PolicyInsuredPerson implements IInsuredItem, Serializable {
	private static final long serialVersionUID = -1810680158208016018L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LPOLINSURPERSON_GEN")
	private String id;

	@Column(name = "AGE")
	private int age;
	private double sumInsured;
	private double basicTermPremium;
	private double premium;
	private double addOnTermPremium;
	private double endorsementNetBasicPremium;;
	private double endorsementNetAddonPremium;
	private double interest;
	@Column(name = "INPERSONCODENO")
	private String insPersonCodeNo;
	@Column(name = "INPERSONGROUPCODENO")
	private String inPersonGroupCodeNo;
	private String initialId;
	private String idNo;
	private String fatherName;
	private String parentName;
	private String parentIdNo;
	@Enumerated(value = EnumType.STRING)
	private IdType parentIdType;
	private Date parentDOB;
	private int unit;
	private int weight;
	private int height;
	private String phone;
	private double premiumRate;

	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	@Enumerated(value = EnumType.STRING)
	private Gender gender;

	@Enumerated(value = EnumType.STRING)
	private IdType idType;

	@Enumerated(EnumType.STRING)
	private EndorsementStatus endorsementStatus;

	@Enumerated(EnumType.STRING)
	private ClaimStatus claimStatus;

	@Enumerated(value = EnumType.STRING)
	private ClassificationOfHealth clsOfHealth;

	@Embedded
	private ResidentAddress residentAddress;

	@Embedded
	private Name name;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTID", referencedColumnName = "ID")
	private Product product;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OCCUPATIONID", referencedColumnName = "ID")
	private Occupation occupation;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RISKYOCCUPATIONID", referencedColumnName = "ID")
	private RiskyOccupation riskyOccupation;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMERID", referencedColumnName = "ID")
	private Customer customer;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TYPESOFSPORTID", referencedColumnName = "ID")
	private TypesOfSport typesOfSport;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RELATIONSHIPID", referencedColumnName = "ID")
	private RelationShip relationship;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCHOOLID", referencedColumnName = "ID")
	private School school;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GRATEINFOID", referencedColumnName = "ID")
	private GradeInfo gradeInfo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LIFEPOLICYID", referencedColumnName = "ID")
	private LifePolicy lifePolicy;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "policyInsuredPerson", orphanRemoval = true)
	private List<PolicyInsuredPersonAttachment> attachmentList;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "HOLDERID", referencedColumnName = "ID")
	private List<Attachment> birthCertificateAttachment;

//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "policyInsuredPerson", orphanRemoval = true)
//	private List<PolicyInsuredPersonAddon> policyInsuredPersonAddOnList;
//
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//	@JoinColumn(name = "REFERENCEID", referencedColumnName = "ID")
//	private List<PolicyInsuredPersonKeyFactorValue> policyInsuredPersonkeyFactorValueList;
//
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "policyInsuredPerson", orphanRemoval = true)
	private List<PolicyInsuredPersonBeneficiaries> policyInsuredPersonBeneficiariesList;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public PolicyInsuredPerson() {
	}

	public PolicyInsuredPerson(ProposalInsuredPerson insuredPerson) {
		this.dateOfBirth = insuredPerson.getDateOfBirth();
		this.clsOfHealth = insuredPerson.getClsOfHealth();
		this.sumInsured = insuredPerson.getProposedSumInsured();
		this.product = insuredPerson.getProduct();
		this.premium = insuredPerson.getProposedPremium();
		this.basicTermPremium = insuredPerson.getBasicTermPremium();
		this.addOnTermPremium = insuredPerson.getAddOnTermPremium();
		this.endorsementNetBasicPremium = insuredPerson.getEndorsementNetBasicPremium();
		this.endorsementNetAddonPremium = insuredPerson.getEndorsementNetAddonPremium();
		this.interest = insuredPerson.getInterest();
		this.weight = insuredPerson.getWeight();
		this.height = insuredPerson.getHeight();
		this.premiumRate = insuredPerson.getPremiumRate();
		this.insPersonCodeNo = insuredPerson.getInsPersonCodeNo();
		this.endorsementStatus = insuredPerson.getEndorsementStatus();
		this.initialId = insuredPerson.getInitialId();
		this.idNo = insuredPerson.getIdNo();
		this.idType = insuredPerson.getIdType();
		this.name = insuredPerson.getName();
		this.gender = insuredPerson.getGender();
		this.residentAddress = insuredPerson.getResidentAddress();
		this.occupation = insuredPerson.getOccupation();
		this.fatherName = insuredPerson.getFatherName();
		this.customer = insuredPerson.getCustomer();
		this.age = insuredPerson.getAge();
		this.inPersonGroupCodeNo = insuredPerson.getInPersonGroupCodeNo();
		this.typesOfSport = insuredPerson.getTypesOfSport();
		this.unit = insuredPerson.getUnit();
		this.relationship = insuredPerson.getRelationship();
		this.riskyOccupation = insuredPerson.getRiskyOccupation();
		this.phone = insuredPerson.getPhone();
		this.relationship = insuredPerson.getRelationship();
		this.parentName = insuredPerson.getParentName();
		this.parentDOB = insuredPerson.getParentDOB();
		this.parentIdType = insuredPerson.getParentIdType();
		this.parentIdNo = insuredPerson.getParentIdNo();
		this.school = insuredPerson.getSchool();
		this.gradeInfo = insuredPerson.getGradeInfo();
		for (Attachment attachment : insuredPerson.getBirthCertificateAttachment()) {
			addBirthCertificateAttachment(new Attachment(attachment));
		}
		for (InsuredPersonAttachment attachment : insuredPerson.getAttachmentList()) {
			addAttachment(new PolicyInsuredPersonAttachment(attachment));
		}
//		for (InsuredPersonAddon addOn : insuredPerson.getInsuredPersonAddOnList()) {
//			addInsuredPersonAddOn(new PolicyInsuredPersonAddon(addOn));
//		}
//		for (InsuredPersonKeyFactorValue keyFactorValue : insuredPerson.getKeyFactorValueList()) {
//			addPolicyInsuredPersonKeyFactorValue(new PolicyInsuredPersonKeyFactorValue(keyFactorValue));
//		}
		for (InsuredPersonBeneficiaries insuredPersonBeneficiaries : insuredPerson.getInsuredPersonBeneficiariesList()) {
			addInsuredPersonBeneficiaries(new PolicyInsuredPersonBeneficiaries(insuredPersonBeneficiaries));
		}
	}

//	public PolicyInsuredPerson(PolicyInsuredPersonHistory history) {
//		this.age = history.getAge();
//		this.sumInsured = history.getSumInsured();
//		this.premium = history.getPremium();
//		this.basicTermPremium = history.getBasicTermPremium();
//		this.addOnTermPremium = history.getAddOnTermPremium();
//		this.endorsementNetBasicPremium = history.getEndorsementNetBasicPremium();
//		this.endorsementNetAddonPremium = history.getEndorsementNetAddonPremium();
//		this.interest = history.getInterest();
//		this.weight = history.getWeight();
//		this.height = history.getHeight();
//		this.premiumRate = history.getPremiumRate();
//		this.insPersonCodeNo = history.getInPersonCodeNo();
//		this.inPersonGroupCodeNo = history.getInPersonGroupCodeNo();
//		this.initialId = history.getInitialId();
//		this.idNo = history.getIdNo();
//		this.fatherName = history.getFatherName();
//		this.dateOfBirth = history.getDateOfBirth();
//		this.gender = history.getGender();
//		this.idType = history.getIdType();
//		this.endorsementStatus = history.getEndorsementStatus();
//		this.claimStatus = history.getClaimStatus();
//		this.clsOfHealth = history.getClsOfHealth();
//		this.residentAddress = history.getResidentAddress();
//		this.name = history.getName();
//		this.product = history.getProduct();
//		this.occupation = history.getOccupation();
//		this.customer = history.getCustomer();
//		this.typesOfSport = history.getTypesOfSport();
//		this.relationship = history.getRelationship();
////		this.riskyOccupation = history.getRiskyOccupation();
//		this.unit = history.getUnit();
//		// this.phone = history.getph
//		this.relationship = history.getRelationship();
//		this.parentName = history.getParentName();
//		this.parentDOB = history.getParentDOB();
//		this.parentIdType = history.getParentIdType();
//		this.parentIdNo = history.getParentIdNo();
//		this.school = history.getSchool();
//		this.gradeInfo = history.getGradeInfo();
//		for (Attachment attachment : history.getBirthCertificateAttachment()) {
//			addBirthCertificateAttachment(new Attachment(attachment));
//		}
//		for (PolicyInsuredPersonAttachmentHistory attachment : history.getAttachmentList()) {
//			addAttachment(new PolicyInsuredPersonAttachment(attachment));
//		}
//
////		for (PolicyInsuredPersonKeyFactorValueHistory keyFactorValue : history.getPolicyInsuredPersonkeyFactorValueList()) {
////			addPolicyInsuredPersonKeyFactorValue(new PolicyInsuredPersonKeyFactorValue(keyFactorValue));
////		}
////
//		for (PolicyInsuredPersonBeneficiariesHistory insuredPersonBeneficiaries : history.getPolicyInsuredPersonBeneficiariesList()) {
//			addInsuredPersonBeneficiaries(new PolicyInsuredPersonBeneficiaries(insuredPersonBeneficiaries));
//		}
////
////		for (PolicyInsuredPersonAddonHistory addOn : history.getPolicyInsuredPersonAddOnList()) {
////			addInsuredPersonAddOn(new PolicyInsuredPersonAddon(addOn));
////		}
//
//	}

	public PolicyInsuredPerson(InsuredPersonInfoDTO dto) {
		this.age = dto.getAge();
		this.sumInsured = dto.getSumInsuredInfo();
		this.premium = dto.getPremium();
		this.basicTermPremium = dto.getBasicTermPremium();
		this.addOnTermPremium = dto.getAddOnTermPremium();
		this.endorsementNetBasicPremium = dto.getEndorsementBasicPremium();
		this.endorsementNetAddonPremium = dto.getEndorsementAddonPremium();
		this.interest = dto.getInterest();
		this.insPersonCodeNo = dto.getInsPersonCodeNo();
		this.inPersonGroupCodeNo = dto.getInPersonGroupCodeNo();
		this.initialId = dto.getInitialId();
		this.idNo = dto.getIdNo();
		this.fatherName = dto.getFatherName();
		this.dateOfBirth = dto.getDateOfBirth();
		this.gender = dto.getGender();
		this.idType = dto.getIdType();
		this.endorsementStatus = dto.getEndorsementStatus();
		this.claimStatus = dto.getClaimStatus();
		this.clsOfHealth = dto.getClassificationOfHealth();
		this.residentAddress = dto.getResidentAddress();
		this.name = dto.getName();
		this.product = dto.getProduct();
		this.occupation = dto.getOccupation();
		this.customer = dto.getCustomer();
		this.typesOfSport = dto.getTypesOfSport();
		this.relationship = dto.getRelationship();
		this.riskyOccupation = dto.getRiskyOccupation();
		this.relationship = dto.getRelationship();
		this.parentName = dto.getParentName();
		this.parentDOB = dto.getParentDOB();
		this.parentIdType = dto.getParentIdType();
		this.parentIdNo = dto.getParentIdNo();
		this.school = dto.getSchool();
		this.gradeInfo = dto.getGradeInfo();
		for (Attachment attachment : dto.getBirthCertificateAttachments()) {
			addBirthCertificateAttachment(new Attachment(attachment));
		}
//		for (PolicyInsuredPersonAttachment attach : dto.getPrePolicyAttachmentList()) {
//			addAttachment(attach);
//		}
//		for (PolicyInsuredPersonKeyFactorValue kfv : dto.getPolicyKeyFactorValueList()) {
//			addPolicyInsuredPersonKeyFactorValue(kfv);
//		}
		for (BeneficiariesInfoDTO beneficiary : dto.getBeneficiariesInfoDTOList()) {
			addInsuredPersonBeneficiaries(new PolicyInsuredPersonBeneficiaries(beneficiary));
		}
//		for (InsuredPersonAddOnDTO addOn : dto.getInsuredPersonAddOnDTOMap().values()) {
//			addInsuredPersonAddOn(new PolicyInsuredPersonAddon(addOn));
//		}
		if (dto.isExistsEntity()) {
			this.id = dto.getTempId();
			this.version = dto.getVersion();
		}
	}

	public PolicyInsuredPerson(Date dateOfBirth, double sumInsured, Product product, LifePolicy lifePolicy, int periodMonth, Date startDate, Date endDate, double premium,
			double endorsementNetBasicPremium, double endorsementNetAddonPremium, double interest, String insPersonCodeNo, EndorsementStatus endorsementStatus,
			String inPersonGroupCodeNo) {
		this.endorsementStatus = endorsementStatus;
		this.dateOfBirth = dateOfBirth;
		this.sumInsured = sumInsured;
		this.product = product;
		this.lifePolicy = lifePolicy;
		this.premium = premium;
		this.endorsementNetBasicPremium = endorsementNetAddonPremium;
		this.endorsementNetAddonPremium = endorsementNetAddonPremium;
		this.insPersonCodeNo = insPersonCodeNo;
		this.interest = interest;
		this.inPersonGroupCodeNo = inPersonGroupCodeNo;
	}

	public PolicyInsuredPerson(Date dateOfBirth, double sumInsured, Product product, LifePolicy lifePolicy, int periodMonth, Date startDate, Date endDate, double premium,
			String initialId, String idNo, IdType idType, Name name, Gender gender, ResidentAddress residentAddress, Occupation occupation, String fatherName,
			double endorsementNetBasicPremium, double endorsementNetAddonPremium, double interest, EndorsementStatus status, String insPersonCodeNo, Customer customer, int age,
			String inPersonGroupCodeNo, int paymentTerm, double basicTermPremium, double addOnTermPremium, ClaimStatus claimStatus) {
		this.dateOfBirth = dateOfBirth;
		this.sumInsured = sumInsured;
		this.product = product;
		this.lifePolicy = lifePolicy;
		this.premium = premium;
		this.initialId = initialId;
		this.idNo = idNo;
		this.idType = idType;
		this.name = name;
		this.residentAddress = residentAddress;
		this.gender = gender;
		this.occupation = occupation;
		this.fatherName = fatherName;
		this.endorsementNetBasicPremium = endorsementNetBasicPremium;
		this.endorsementNetAddonPremium = endorsementNetAddonPremium;
		this.interest = interest;
		this.endorsementStatus = status;
		this.insPersonCodeNo = insPersonCodeNo;
		this.customer = customer;
		this.age = age;
		this.inPersonGroupCodeNo = inPersonGroupCodeNo;
		this.basicTermPremium = basicTermPremium;
		this.addOnTermPremium = addOnTermPremium;
		this.claimStatus = claimStatus;
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
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

	public EndorsementStatus getEndorsementStatus() {
		return endorsementStatus;
	}

	public void setEndorsementStatus(EndorsementStatus endorsementStatus) {
		this.endorsementStatus = endorsementStatus;
	}

	public double getEndorsementNetBasicPremium() {
		return endorsementNetBasicPremium;
	}

	public void setEndorsementNetBasicPremium(double endorsementNetBasicPremium) {
		this.endorsementNetBasicPremium = endorsementNetBasicPremium;
	}

	public double getEndorsementNetAddonPremium() {
		return endorsementNetAddonPremium;
	}

	public void setEndorsementNetAddonPremium(double endorsementNetAddonPremium) {
		this.endorsementNetAddonPremium = endorsementNetAddonPremium;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public String getInsPersonCodeNo() {
		return insPersonCodeNo;
	}

	public void setInsPersonCodeNo(String insPersonCodeNo) {
		this.insPersonCodeNo = insPersonCodeNo;
	}

	public String getInPersonGroupCodeNo() {
		return inPersonGroupCodeNo;
	}

	public void setInPersonGroupCodeNo(String inPersonGroupCodeNo) {
		this.inPersonGroupCodeNo = inPersonGroupCodeNo;
	}

	public List<PolicyInsuredPersonAttachment> getAttachmentList() {
		if (this.attachmentList == null) {
			this.attachmentList = new ArrayList<PolicyInsuredPersonAttachment>();
		}
		return this.attachmentList;
	}

	public void setAttachmentList(List<PolicyInsuredPersonAttachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public LifePolicy getLifePolicy() {
		return lifePolicy;
	}

	public void setLifePolicy(LifePolicy lifePolicy) {
		this.lifePolicy = lifePolicy;
	}

//	public List<PolicyInsuredPersonAddon> getPolicyInsuredPersonAddOnList() {
//		if (policyInsuredPersonAddOnList == null) {
//			policyInsuredPersonAddOnList = new ArrayList<PolicyInsuredPersonAddon>();
//		}
//		return policyInsuredPersonAddOnList;
//	}
//
//	public void setPolicyInsuredPersonAddOnList(List<PolicyInsuredPersonAddon> policyInsuredPersonAddOnList) {
//		this.policyInsuredPersonAddOnList = policyInsuredPersonAddOnList;
//	}
//
//	public List<PolicyInsuredPersonKeyFactorValue> getPolicyInsuredPersonkeyFactorValueList() {
//		if (policyInsuredPersonkeyFactorValueList == null) {
//			policyInsuredPersonkeyFactorValueList = new ArrayList<PolicyInsuredPersonKeyFactorValue>();
//		}
//		return policyInsuredPersonkeyFactorValueList;
//	}
//
//	public void setPolicyInsuredPersonkeyFactorValueList(List<PolicyInsuredPersonKeyFactorValue> policyInsuredPersonkeyFactorValueList) {
//		this.policyInsuredPersonkeyFactorValueList = policyInsuredPersonkeyFactorValueList;
//	}

//	public String getKeyFactorValueListForDetails() {
//		StringBuffer buffer = new StringBuffer();
//		if (getPolicyInsuredPersonkeyFactorValueList().size() > 0) {
//			String id = getPolicyInsuredPersonkeyFactorValueList().get(getPolicyInsuredPersonkeyFactorValueList().size() - 1).getKeyFactor().getId();
//			for (PolicyInsuredPersonKeyFactorValue keyfactorValue : getPolicyInsuredPersonkeyFactorValueList()) {
//				buffer.append(keyfactorValue.getKeyFactor().getValue()).append(" - ").append(keyfactorValue.getValue())
//						.append(id == keyfactorValue.getKeyFactor().getId() ? "" : " , ");
//			}
//		}
//		return buffer.toString();
//	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

//	public double getAddOnPremium() {
//		double premium = 0.0;
//		if (policyInsuredPersonAddOnList != null && !policyInsuredPersonAddOnList.isEmpty()) {
//			for (PolicyInsuredPersonAddon pia : policyInsuredPersonAddOnList) {
//				premium = Utils.getTwoDecimalPoint(premium + pia.getPremium());
//			}
//		}
//		return premium;
//	}
//
//	public double getTotalPremium() {
//		return Utils.getTwoDecimalPoint(premium + getAddOnPremium());
//	}
//
//	public double getTotalTermPermium() {
//		return Utils.getTwoDecimalPoint(getBasicTermPremium() + getAddOnTermPremium());
//	}
//
//	public double getAddOnSumInsure() {
//		double premium = 0.0;
//		if (policyInsuredPersonAddOnList != null && !policyInsuredPersonAddOnList.isEmpty()) {
//			for (PolicyInsuredPersonAddon pia : policyInsuredPersonAddOnList) {
//				premium = premium + pia.getSumInsured();
//			}
//		}
//		return premium;
//	}

	public double getSuminsuredPerUnit() {
		double suminsuredPerUnit = 0.0;
		suminsuredPerUnit = unit * product.getSumInsuredPerUnit();
		return suminsuredPerUnit;
	}

	public List<PolicyInsuredPersonBeneficiaries> getPolicyInsuredPersonBeneficiariesList() {
		if (this.policyInsuredPersonBeneficiariesList == null) {
			this.policyInsuredPersonBeneficiariesList = new ArrayList<PolicyInsuredPersonBeneficiaries>();
		}
		return this.policyInsuredPersonBeneficiariesList;
	}

	public void setPolicyInsuredPersonBeneficiariesList(List<PolicyInsuredPersonBeneficiaries> policyInsuredPersonBeneficiariesList) {
		this.policyInsuredPersonBeneficiariesList = policyInsuredPersonBeneficiariesList;
	}

	public void addAttachment(PolicyInsuredPersonAttachment attachment) {
		attachment.setPolicyInsuredPerson(this);
		getAttachmentList().add(attachment);
	}

	public void addBirthCertificateAttachment(Attachment attachment) {
		getBirthCertificateAttachment().add(attachment);
	}

//	public void addInsuredPersonAddOn(PolicyInsuredPersonAddon policyInsuredPersonAddOn) {
//		policyInsuredPersonAddOn.setPolicyInsuredPersonInfo(this);
//		getPolicyInsuredPersonAddOnList().add(policyInsuredPersonAddOn);
//	}
//
//	public void addPolicyInsuredPersonKeyFactorValue(PolicyInsuredPersonKeyFactorValue keyFactorValue) {
//		// keyFactorValue.setPolicyInsuredPersonInfo(this);
//		getPolicyInsuredPersonkeyFactorValueList().add(keyFactorValue);
//	}

	public void addInsuredPersonBeneficiaries(PolicyInsuredPersonBeneficiaries policyInsuredPersonBeneficiaries) {
		policyInsuredPersonBeneficiaries.setPolicyInsuredPerson(this);
		getPolicyInsuredPersonBeneficiariesList().add(policyInsuredPersonBeneficiaries);
	}

	public ClaimStatus getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(ClaimStatus claimStatus) {
		this.claimStatus = claimStatus;
	}

	public ClassificationOfHealth getClsOfHealth() {
		return clsOfHealth;
	}

	public void setClsOfHealth(ClassificationOfHealth clsOfHealth) {
		this.clsOfHealth = clsOfHealth;
	}

	public void setPaymentTimes(int paymentTimes) {
		// do nothing
	}

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

	/**
	 * @see org.ace.insurance.common.interfaces.IPolicy#getTotalPremium()
	 */

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
		if (idNo == null || idNo.isEmpty()) {
			return "Still Applying";
		}
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

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public String getFullName() {
		String result = "";
		if (name != null) {
			if (initialId != null && !initialId.isEmpty()) {
				result = initialId.trim();
			}
			if (name.getFirstName() != null && !name.getFirstName().isEmpty()) {
				result = result + " " + name.getFirstName().trim();
			}
			if (name.getMiddleName() != null && !name.getMiddleName().isEmpty()) {
				result = result + " " + name.getMiddleName().trim();
			}
			if (name.getLastName() != null && !name.getLastName().isEmpty()) {
				result = result + " " + name.getLastName().trim();
			}
		}
		return result;
	}

	public String getJSPName() {
		String result = "";
		if (name != null) {

			if (name.getFirstName() != null && !name.getFirstName().isEmpty()) {
				result = result + " " + name.getFirstName().trim();
			}
			if (name.getMiddleName() != null && !name.getMiddleName().isEmpty()) {
				result = result + " " + name.getMiddleName().trim();
			}
			if (name.getLastName() != null && !name.getLastName().isEmpty()) {
				result = result + " " + name.getLastName().trim();
			}
		}
		return result;
	}

	public Occupation getOccupation() {
		return occupation;
	}

	public String getOccupationName() {
		if (occupation != null)
			return occupation.getName();
		else
			return "-";
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public TypesOfSport getTypesOfSport() {
		return typesOfSport;
	}

	public String getTypesOfSportName() {
		if (typesOfSport != null)
			return typesOfSport.getName();
		else
			return "";
	}

	public void setTypesOfSport(TypesOfSport typesOfSport) {
		this.typesOfSport = typesOfSport;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
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

	public RelationShip getRelationship() {
		return relationship;
	}

	public void setRelationship(RelationShip relationship) {
		this.relationship = relationship;
	}

	public RiskyOccupation getRiskyOccupation() {
		return riskyOccupation;
	}

	public void setRiskyOccupation(RiskyOccupation riskyOccupation) {
		this.riskyOccupation = riskyOccupation;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhoneForView() {
		if (phone != null && !phone.isEmpty())
			return phone;
		else
			return "-";
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getParentIdNo() {
		return parentIdNo;
	}

	public void setParentIdNo(String parentIdNo) {
		this.parentIdNo = parentIdNo;
	}

	public IdType getParentIdType() {
		return parentIdType;
	}

	public void setParentIdType(IdType parentIdType) {
		this.parentIdType = parentIdType;
	}

	public Date getParentDOB() {
		return parentDOB;
	}

	public void setParentDOB(Date parentDOB) {
		this.parentDOB = parentDOB;
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

	public List<Attachment> getBirthCertificateAttachment() {
		if (this.birthCertificateAttachment == null) {
			this.birthCertificateAttachment = new ArrayList<Attachment>();
		}
		return birthCertificateAttachment;
	}

	public void setBirthCertificateAttachment(List<Attachment> birthCertificateAttachment) {
		this.birthCertificateAttachment = birthCertificateAttachment;
	}

	public String getSchoolName() {
		if (school != null)
			return school.getName();
		return "-";
	}

	public String getSchoolAddress() {
		if (school != null && school.getAddress() != null)
			return school.getFullAddress();
		return "-";
	}

	public String getGradeInfoName() {
		if (gradeInfo != null)
			return gradeInfo.getName();
		return "-";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(addOnTermPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + age;
		temp = Double.doubleToLongBits(basicTermPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((claimStatus == null) ? 0 : claimStatus.hashCode());
		result = prime * result + ((clsOfHealth == null) ? 0 : clsOfHealth.hashCode());
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		temp = Double.doubleToLongBits(endorsementNetAddonPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(endorsementNetBasicPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((endorsementStatus == null) ? 0 : endorsementStatus.hashCode());
		result = prime * result + ((fatherName == null) ? 0 : fatherName.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + height;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idNo == null) ? 0 : idNo.hashCode());
		result = prime * result + ((idType == null) ? 0 : idType.hashCode());
		result = prime * result + ((inPersonGroupCodeNo == null) ? 0 : inPersonGroupCodeNo.hashCode());
		result = prime * result + ((initialId == null) ? 0 : initialId.hashCode());
		result = prime * result + ((insPersonCodeNo == null) ? 0 : insPersonCodeNo.hashCode());
		temp = Double.doubleToLongBits(interest);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((lifePolicy == null) ? 0 : lifePolicy.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((occupation == null) ? 0 : occupation.hashCode());
		temp = Double.doubleToLongBits(premium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(premiumRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((relationship == null) ? 0 : relationship.hashCode());
		result = prime * result + ((residentAddress == null) ? 0 : residentAddress.hashCode());
		result = prime * result + ((riskyOccupation == null) ? 0 : riskyOccupation.hashCode());
		temp = Double.doubleToLongBits(sumInsured);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((typesOfSport == null) ? 0 : typesOfSport.hashCode());
		result = prime * result + unit;
		result = prime * result + version;
		result = prime * result + weight;
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
		PolicyInsuredPerson other = (PolicyInsuredPerson) obj;
		if (Double.doubleToLongBits(addOnTermPremium) != Double.doubleToLongBits(other.addOnTermPremium))
			return false;
		if (age != other.age)
			return false;
		if (Double.doubleToLongBits(basicTermPremium) != Double.doubleToLongBits(other.basicTermPremium))
			return false;
		if (claimStatus != other.claimStatus)
			return false;
		if (clsOfHealth != other.clsOfHealth)
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (Double.doubleToLongBits(endorsementNetAddonPremium) != Double.doubleToLongBits(other.endorsementNetAddonPremium))
			return false;
		if (Double.doubleToLongBits(endorsementNetBasicPremium) != Double.doubleToLongBits(other.endorsementNetBasicPremium))
			return false;
		if (endorsementStatus != other.endorsementStatus)
			return false;
		if (fatherName == null) {
			if (other.fatherName != null)
				return false;
		} else if (!fatherName.equals(other.fatherName))
			return false;
		if (gender != other.gender)
			return false;
		if (height != other.height)
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
		if (inPersonGroupCodeNo == null) {
			if (other.inPersonGroupCodeNo != null)
				return false;
		} else if (!inPersonGroupCodeNo.equals(other.inPersonGroupCodeNo))
			return false;
		if (initialId == null) {
			if (other.initialId != null)
				return false;
		} else if (!initialId.equals(other.initialId))
			return false;
		if (insPersonCodeNo == null) {
			if (other.insPersonCodeNo != null)
				return false;
		} else if (!insPersonCodeNo.equals(other.insPersonCodeNo))
			return false;
		if (Double.doubleToLongBits(interest) != Double.doubleToLongBits(other.interest))
			return false;
		if (lifePolicy == null) {
			if (other.lifePolicy != null)
				return false;
		} else if (!lifePolicy.equals(other.lifePolicy))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (occupation == null) {
			if (other.occupation != null)
				return false;
		} else if (!occupation.equals(other.occupation))
			return false;
		if (Double.doubleToLongBits(premium) != Double.doubleToLongBits(other.premium))
			return false;
		if (Double.doubleToLongBits(premiumRate) != Double.doubleToLongBits(other.premiumRate))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
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
		if (riskyOccupation == null) {
			if (other.riskyOccupation != null)
				return false;
		} else if (!riskyOccupation.equals(other.riskyOccupation))
			return false;
		if (Double.doubleToLongBits(sumInsured) != Double.doubleToLongBits(other.sumInsured))
			return false;
		if (typesOfSport == null) {
			if (other.typesOfSport != null)
				return false;
		} else if (!typesOfSport.equals(other.typesOfSport))
			return false;
		if (unit != other.unit)
			return false;
		if (version != other.version)
			return false;
		if (weight != other.weight)
			return false;
		return true;
	}

	@Override
	public double getAddOnSumInsure() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getAddOnPremium() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getTotalPremium() {
		// TODO Auto-generated method stub
		return 0;
	}

}
