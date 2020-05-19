package org.tat.fni.api.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.KeyFactor;
import org.tat.fni.api.common.MAO01;
import org.tat.fni.api.common.RegNoSorter;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;
import org.tat.fni.api.common.Utils;



@Entity
@Table(name = TableName.MEDICALPROPOSALINSUREDPERSON)
@TableGenerator(name = "MEDICALPROPOSALINSUREDPERSON_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "MEDICALPROPOSALINSUREDPERSON_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class MedicalProposalInsuredPerson {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "MEDICALPROPOSALINSUREDPERSON_GEN")
	private String id;

	private int age;
	private int unit;

	private double sumInsured;
	private double premium;
	private double basicTermPremium;
	private double addOnTermPremium;

	private boolean isPaidPremiumForPaidup;
	private boolean needMedicalCheckup;
	private boolean sameCustomer;
	private boolean approved;

	private String rejectReason;

	@Column(name = "INSPERSONCODENO")
	private String insPersonCodeNo;

	@Column(name = "INPERSONGROUPCODENO")
	private String inPersonGroupCodeNo;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RELATIONSHIPID", referencedColumnName = "ID")
	private RelationShip relationship;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTID", referencedColumnName = "ID")
	private Product product;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CUSTOMERID", referencedColumnName = "ID")
	private Customer customer;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "GUARDIANID", referencedColumnName = "ID")
	private MedicalProposalInsuredPersonGuardian guardian;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "PROPOSALINSUREDPERSONID", referencedColumnName = "ID")
	private List<MedicalProposalInsuredPersonAttachment> attachmentList;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "MEDIPROPOSALINSUREDPERSONID", referencedColumnName = "ID")
	private List<MedicalProposalInsuredPersonAddOn> insuredPersonAddOnList;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "REFERENCEID", referencedColumnName = "ID")
	private List<MedicalKeyFactorValue> keyFactorValueList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "INSUREDPERSONID", referencedColumnName = "ID")
	private List<MedicalPersonHistoryRecord> medicalPersonHistoryRecordList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "INSUREDPERSONID", referencedColumnName = "ID")
	private List<MedicalProposalInsuredPersonBeneficiaries> insuredPersonBeneficiariesList;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "INSUREDPERSONID", referencedColumnName = "ID")
	private List<SurveyQuestionAnswer> surveyQuestionAnswerList;

	@Embedded
	private UserRecorder recorder;

	@Version
	private int version;

	public MedicalProposalInsuredPerson() {
	}

//	public MedicalProposalInsuredPerson(MedicalPolicyInsuredPerson policyInsuredPerson) {
//		this.customer = policyInsuredPerson.getCustomer();
//		this.product = policyInsuredPerson.getProduct();
//		this.relationship = policyInsuredPerson.getRelationship();
//		this.insPersonCodeNo = policyInsuredPerson.getInsPersonCodeNo();
//		this.inPersonGroupCodeNo = policyInsuredPerson.getInPersonGroupCodeNo();
//		this.age = policyInsuredPerson.getAge();
//		this.unit = policyInsuredPerson.getUnit();
//		this.sumInsured = policyInsuredPerson.getSumInsured();
//		this.premium = policyInsuredPerson.getPremium();
//		this.basicTermPremium = policyInsuredPerson.getBasicTermPremium();
//		this.addOnTermPremium = policyInsuredPerson.getAddOnTermPremium();
//		this.relationship = policyInsuredPerson.getRelationship();
//
//		if (policyInsuredPerson.getGuardian() != null) {
//			this.guardian = new MedicalProposalInsuredPersonGuardian(policyInsuredPerson.getGuardian());
//		}
//
//		for (MedicalPolicyInsuredPersonAttachment attachment : policyInsuredPerson.getAttachmentList()) {
//			addAttachment(new MedicalProposalInsuredPersonAttachment(attachment));
//		}
//
//		for (MedicalPolicyKeyFactorValue keyFactorValue : policyInsuredPerson.getKeyFactorValueList()) {
//			addMedicalKeyFactorValue(new MedicalKeyFactorValue(keyFactorValue));
//		}
//
//		for (MedicalPolicyInsuredPersonBeneficiaries insuredPersonBeneficiaries : policyInsuredPerson.getPolicyInsuredPersonBeneficiariesList()) {
//			addBeneficiaries(new MedicalProposalInsuredPersonBeneficiaries(insuredPersonBeneficiaries));
//		}
//
//		for (MedicalPersonHistoryRecord record : policyInsuredPerson.getMedicalPersonHistoryRecordList()) {
//			addHistoryRecord(new MedicalPersonHistoryRecord(record));
//		}
//
//		for (MedicalPolicyInsuredPersonAddOn addOn : policyInsuredPerson.getPolicyInsuredPersonAddOnList()) {
//			addInsuredPersonAddon(new MedicalProposalInsuredPersonAddOn(addOn));
//		}
//	}

	public boolean isSameCustomer() {
		return sameCustomer;
	}

	public void setSameCustomer(boolean sameCustomer) {
		this.sameCustomer = sameCustomer;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean getIsPaidPremiumForPaidup() {
		return isPaidPremiumForPaidup;
	}

	public void setIsPaidPremiumForPaidup(boolean isPaidPremiumForPaidup) {
		this.isPaidPremiumForPaidup = isPaidPremiumForPaidup;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public boolean isNeedMedicalCheckup() {
		return needMedicalCheckup;
	}

	public void setNeedMedicalCheckup(boolean needMedicalCheckup) {
		this.needMedicalCheckup = needMedicalCheckup;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public RelationShip getRelationship() {
		return relationship;
	}

	public MedicalProposalInsuredPersonGuardian getGuardian() {
		return guardian;
	}

	public void setGuardian(MedicalProposalInsuredPersonGuardian guardian) {
		this.guardian = guardian;
	}

	public void setRelationship(RelationShip relationship) {
		this.relationship = relationship;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
		loadKeyFactor(product);
	}

	public void loadKeyFactor(Product product) {
		keyFactorValueList = new ArrayList<MedicalKeyFactorValue>();
		for (KeyFactor kf : product.getKeyFactorList()) {
			MedicalKeyFactorValue insKf = new MedicalKeyFactorValue(kf);
			insKf.setKeyFactor(kf);
			keyFactorValueList.add(insKf);
		}
	}

	public List<MedicalPersonHistoryRecord> getMedicalPersonHistoryRecordList() {
		if (medicalPersonHistoryRecordList == null) {
			medicalPersonHistoryRecordList = new ArrayList<MedicalPersonHistoryRecord>();
		}
		return medicalPersonHistoryRecordList;
	}

	public void setMedicalPersonHistoryRecordList(List<MedicalPersonHistoryRecord> medicalPersonHistoryRecordList) {
		this.medicalPersonHistoryRecordList = medicalPersonHistoryRecordList;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public List<MedicalProposalInsuredPersonAttachment> getAttachmentList() {
		if (attachmentList == null) {
			attachmentList = new ArrayList<MedicalProposalInsuredPersonAttachment>();
		}
		return attachmentList;
	}

	public void setAttachmentList(List<MedicalProposalInsuredPersonAttachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	/* sort by addOn Name */
	public List<MedicalProposalInsuredPersonAddOn> getInsuredPersonAddOnList() {
		if (insuredPersonAddOnList == null) {
			insuredPersonAddOnList = new ArrayList<MedicalProposalInsuredPersonAddOn>();
		}
		if (insuredPersonAddOnList.isEmpty()) {
			return insuredPersonAddOnList;
		} else {
			RegNoSorter<MedicalProposalInsuredPersonAddOn> sorter = new RegNoSorter<MedicalProposalInsuredPersonAddOn>(insuredPersonAddOnList);
			return sorter.getSortedList();
		}
	}

	public List<MedicalProposalInsuredPersonAddOn> getInsuredPersonAddOnListUnSort() {
		if (insuredPersonAddOnList == null) {
			insuredPersonAddOnList = new ArrayList<MedicalProposalInsuredPersonAddOn>();
		}
		return insuredPersonAddOnList;
	}

	public void setInsuredPersonAddOnList(List<MedicalProposalInsuredPersonAddOn> insuredPersonAddOnList) {
		this.insuredPersonAddOnList = insuredPersonAddOnList;
	}

	public List<MedicalKeyFactorValue> getKeyFactorValueList() {
		if (keyFactorValueList == null) {
			keyFactorValueList = new ArrayList<MedicalKeyFactorValue>();
		}
		return keyFactorValueList;
	}

	public void setKeyFactorValueList(List<MedicalKeyFactorValue> keyFactorValueList) {
		this.keyFactorValueList = keyFactorValueList;
	}

	public List<MedicalProposalInsuredPersonBeneficiaries> getInsuredPersonBeneficiariesList() {
		if (insuredPersonBeneficiariesList == null) {
			insuredPersonBeneficiariesList = new ArrayList<MedicalProposalInsuredPersonBeneficiaries>();
		}
		return insuredPersonBeneficiariesList;
	}

	public void setInsuredPersonBeneficiariesList(List<MedicalProposalInsuredPersonBeneficiaries> insuredPersonBeneficiariesList) {
		this.insuredPersonBeneficiariesList = insuredPersonBeneficiariesList;
	}

	public void setPaidPremiumForPaidup(boolean isPaidPremiumForPaidup) {
		this.isPaidPremiumForPaidup = isPaidPremiumForPaidup;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public void addHistoryRecord(MedicalPersonHistoryRecord record) {
		getMedicalPersonHistoryRecordList().add(record);
	}

	public void addInsuredPersonAddon(MedicalProposalInsuredPersonAddOn insuredPersonAddon) {
		getInsuredPersonAddOnListUnSort().add(insuredPersonAddon);
	}

	public void addSurveyQuestion(SurveyQuestionAnswer surveyQuestion) {
		getSurveyQuestionAnswerList().add(surveyQuestion);
	}

	public void addBeneficiaries(MedicalProposalInsuredPersonBeneficiaries insuredPersonBeneficiaries) {
		getInsuredPersonBeneficiariesList().add(insuredPersonBeneficiaries);
	}

	public void addMedicalKeyFactorValue(MedicalKeyFactorValue insuredPersonKeyFactorValue) {
		getKeyFactorValueList().add(insuredPersonKeyFactorValue);
	}

	public void addAttachment(MedicalProposalInsuredPersonAttachment attachment) {
		getAttachmentList().add(attachment);
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
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

	/************************
	 * System Generated Method
	 ************************/

	public List<MAO01> getAddOnDetailList() {
		List<MAO01> resultList = new ArrayList<MAO01>();
		for (MedicalProposalInsuredPersonAddOn addOn : getInsuredPersonAddOnList()) {
			resultList.add(new MAO01(addOn.getAddOn().getName(), addOn.getUnit(), addOn.getPremium()));
		}
		return resultList;
	}

	public String getFullName() {
		return customer.getFullName();
	}

	public String getGuardianName() {
		if (guardian != null) {
			return guardian.getCustomer().getFullName();
		} else {
			return "";
		}
	}

	public double getTotalAddOnUnit() {
		double unit = 0.0;
		for (MedicalProposalInsuredPersonAddOn addOn : insuredPersonAddOnList) {
			unit += addOn.getUnit();
		}
		return unit;
	}

	public double getTotalPremium() {
		double result = 0;
		result = this.premium + getAddOnPremium();
		return result;
	}

	public double getAddOnPremium() {
		double premium = 0.0;
		for (MedicalProposalInsuredPersonAddOn addOn : getInsuredPersonAddOnList()) {
			premium = Utils.getTwoDecimalPoint(premium + addOn.getPremium());
		}
		return premium;
	}

	public double getTermPremium() {
		return basicTermPremium + addOnTermPremium;
	}

	public double getTotalSumInsured() {
		double totalSumInsured = sumInsured;
		for (MedicalProposalInsuredPersonAddOn addOn : getInsuredPersonAddOnList()) {
			totalSumInsured += addOn.getSumInsured();
		}
		return totalSumInsured;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(addOnTermPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + age;
		result = prime * result + (approved ? 1231 : 1237);
		temp = Double.doubleToLongBits(basicTermPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((guardian == null) ? 0 : guardian.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((inPersonGroupCodeNo == null) ? 0 : inPersonGroupCodeNo.hashCode());
		result = prime * result + ((insPersonCodeNo == null) ? 0 : insPersonCodeNo.hashCode());
		result = prime * result + (isPaidPremiumForPaidup ? 1231 : 1237);
		result = prime * result + (needMedicalCheckup ? 1231 : 1237);
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		temp = Double.doubleToLongBits(premium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((rejectReason == null) ? 0 : rejectReason.hashCode());
		result = prime * result + ((relationship == null) ? 0 : relationship.hashCode());
		result = prime * result + (sameCustomer ? 1231 : 1237);
		result = prime * result + unit;
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
		MedicalProposalInsuredPerson other = (MedicalProposalInsuredPerson) obj;
		if (Double.doubleToLongBits(addOnTermPremium) != Double.doubleToLongBits(other.addOnTermPremium))
			return false;
		if (age != other.age)
			return false;
		if (approved != other.approved)
			return false;
		if (Double.doubleToLongBits(basicTermPremium) != Double.doubleToLongBits(other.basicTermPremium))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (guardian == null) {
			if (other.guardian != null)
				return false;
		} else if (!guardian.equals(other.guardian))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (inPersonGroupCodeNo == null) {
			if (other.inPersonGroupCodeNo != null)
				return false;
		} else if (!inPersonGroupCodeNo.equals(other.inPersonGroupCodeNo))
			return false;
		if (insPersonCodeNo == null) {
			if (other.insPersonCodeNo != null)
				return false;
		} else if (!insPersonCodeNo.equals(other.insPersonCodeNo))
			return false;
		if (isPaidPremiumForPaidup != other.isPaidPremiumForPaidup)
			return false;
		if (needMedicalCheckup != other.needMedicalCheckup)
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (Double.doubleToLongBits(premium) != Double.doubleToLongBits(other.premium))
			return false;
		if (rejectReason == null) {
			if (other.rejectReason != null)
				return false;
		} else if (!rejectReason.equals(other.rejectReason))
			return false;
		if (relationship == null) {
			if (other.relationship != null)
				return false;
		} else if (!relationship.equals(other.relationship))
			return false;
		if (sameCustomer != other.sameCustomer)
			return false;
		if (unit != other.unit)
			return false;
		if (version != other.version)
			return false;
		return true;
	}
}
