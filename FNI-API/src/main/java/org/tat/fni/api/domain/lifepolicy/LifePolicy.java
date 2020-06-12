package org.tat.fni.api.domain.lifepolicy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.KeyFactorChecker;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;
import org.tat.fni.api.common.emumdata.EndorsementStatus;
import org.tat.fni.api.common.emumdata.InsuranceType;
import org.tat.fni.api.common.emumdata.ProposalType;
import org.tat.fni.api.common.emumdata.SaleChannelType;
import org.tat.fni.api.common.interfaces.IInsuredItem;
import org.tat.fni.api.common.interfaces.ISorter;
import org.tat.fni.api.common.utils.Utils;
import org.tat.fni.api.domain.Agent;
import org.tat.fni.api.domain.Attachment;
import org.tat.fni.api.domain.BankBranch;
import org.tat.fni.api.domain.Branch;
import org.tat.fni.api.domain.Customer;
import org.tat.fni.api.domain.IPolicy;
import org.tat.fni.api.domain.LifeProposalAttachment;
import org.tat.fni.api.domain.Organization;
import org.tat.fni.api.domain.PaymentType;
import org.tat.fni.api.domain.PolicyStatus;
import org.tat.fni.api.domain.ProductGroup;
import org.tat.fni.api.domain.ProposalInsuredPerson;
import org.tat.fni.api.domain.SalesPoints;
import org.tat.fni.api.domain.User;
import org.tat.fni.api.domain.lifeproposal.LifeProposal;

@Entity
@Table(name = TableName.LIFEPOLICY)
@TableGenerator(name = "LIFEPOLICY_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LIFEPOLICY_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "LifePolicy.findAll", query = "SELECT l FROM LifePolicy l "),
		@NamedQuery(name = "LifePolicy.findAllActiveLifePolicy", query = "SELECT l FROM LifePolicy l WHERE l.policyNo IS NOT NULL AND l.isEndorsementApplied!=1"),
		@NamedQuery(name = "LifePolicy.findByCustomer", query = "SELECT l FROM LifePolicy l WHERE l.policyNo IS NOT NULL AND l.customer.id = :customerId"),
		@NamedQuery(name = "LifePolicy.findByProposalId", query = "SELECT l FROM LifePolicy l WHERE l.lifeProposal.id = :lifeProposalId"),
		@NamedQuery(name = "LifePolicy.findByLifeProposalId", query = "SELECT l FROM LifePolicy l WHERE l.lifeProposal.id = :lifeProposalId"),
		@NamedQuery(name = "LifePolicy.findById", query = "SELECT l FROM LifePolicy l WHERE l.id = :id"),
		@NamedQuery(name = "LifePolicy.findByPolicyId", query = "SELECT l FROM LifePolicy l WHERE l.id = :lifePolicyId"),
		@NamedQuery(name = "LifePolicy.findByPolicyNo", query = "SELECT l FROM LifePolicy l WHERE l.policyNo = :policyNo"),
		@NamedQuery(name = "LifePolicy.findPaymentOrderByReceiptNo", query = "SELECT DISTINCT l FROM LifePolicy l, Payment p WHERE p.receiptNo = :receiptNo AND l.id = p.referenceNo AND p.isPO = TRUE "),
		@NamedQuery(name = "LifePolicy.findPolicyNoById", query = "SELECT l.policyNo FROM LifePolicy l WHERE l.id = :id"),
		@NamedQuery(name = "LifePolicy.increasePrintCount", query = "UPDATE LifePolicy l SET l.printCount = l.printCount + 1 WHERE l.id = :id"),
		@NamedQuery(name = "LifePolicy.updatePaymentDate", query = "UPDATE LifePolicy l SET l.activedPolicyStartDate = :paymentDate, l.activedPolicyEndDate = :paymentValidDate where l.id = :id"),
		@NamedQuery(name = "LifePolicy.updateCommenmanceDate", query = "UPDATE LifePolicy l SET l.commenmanceDate = :commenceDate WHERE l.id = :id"),
		@NamedQuery(name = "LifePolicy.updateEndorsementStatus", query = "UPDATE LifePolicy l SET l.isEndorsementApplied = :isEndorsementApplied WHERE l.id = :id"),
		@NamedQuery(name = "LifePolicy.updateEndorsementStatusAndIssueDate", query = "UPDATE LifePolicy l SET l.isEndorsementApplied = :isEndorsementApplied,l.issueDate=:issueDate WHERE l.id = :id") })
@EntityListeners(IDInterceptor.class)
public class LifePolicy implements IPolicy, Serializable, ISorter {
	private static final long serialVersionUID = 2379164707215020929L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LIFEPOLICY_GEN")
	private String id;

	private boolean delFlag;
	private boolean isCoinsuranceApplied;
	private boolean isEndorsementApplied;
	private boolean isNonFinancialEndorse;
	private int lastPaymentTerm;
	@Column(name = "PERIODOFMONTH")
	private int periodMonth;
	private int printCount;
	private double totalDiscountAmount;
	private double standardExcess;
	private double specialDiscount;
	private double currencyRate;
	private String policyNo;
	private String endorsementNo;

	/* Underwriting payment date */
	@Temporal(TemporalType.TIMESTAMP)
	private Date commenmanceDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ACTIVEDPOLICYSTARTDATE")
	private Date activedPolicyStartDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ACTIVEDPOLICYENDDATE")
	private Date activedPolicyEndDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "COVERAGEDATE")
	private Date coverageDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ISSUEDATE")
	private Date issueDate;

	@Column(name = "ENDORSEMENTCONFIRMDATE")
	@Temporal(TemporalType.DATE)
	private Date endorsementConfirmDate;

	@Column(name = "RENEWALCONFIRMDATE")
	@Temporal(TemporalType.DATE)
	private Date renewalConfirmDate;

	@Enumerated(EnumType.STRING)
	private PolicyStatus policyStatus;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMERID", referencedColumnName = "ID")
	private Customer customer;

	@Enumerated(EnumType.STRING)
	private SaleChannelType saleChannelType;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORGANIZATIONID", referencedColumnName = "ID")
	private Organization organization;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPROVERID", referencedColumnName = "ID")
	private User approvedBy;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCHID", referencedColumnName = "ID")
	private Branch branch;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SALESPOINTSID", referencedColumnName = "ID")
	private SalesPoints salesPoints;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAYMENTTYPEID", referencedColumnName = "ID")
	private PaymentType paymentType;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AGENTID", referencedColumnName = "ID")
	private Agent agent;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SALEBANKID", referencedColumnName = "ID")
	private BankBranch saleBank;

	@OneToOne
	@JoinColumn(name = "PROPOSALID")
	private LifeProposal lifeProposal;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "lifePolicy", orphanRemoval = true)
	private List<PolicyInsuredPerson> policyInsuredPersonList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "lifePolicy", orphanRemoval = true)
	private List<LifePolicyAttachment> attachmentList;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "HOLDERID", referencedColumnName = "ID")
	private List<Attachment> customerMedicalCheckUpAttachmentList;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public LifePolicy() {
	}

	public LifePolicy(LifeProposal lifeProposal) {
		this.customer = lifeProposal.getCustomer();
		this.organization = lifeProposal.getOrganization();
		this.branch = lifeProposal.getBranch();
		this.salesPoints = lifeProposal.getSalesPoints();
		this.paymentType = lifeProposal.getPaymentType();
//		this.saleBank = lifeProposal.getSaleBank();
		this.saleChannelType = lifeProposal.getSaleChannelType();
		this.agent = lifeProposal.getAgent();
		this.lifeProposal = lifeProposal;
		this.periodMonth = lifeProposal.getPeriodMonth();
		this.lastPaymentTerm = lifeProposal.getPaymentTerm();
		this.isEndorsementApplied = false;
//		this.isNonFinancialEndorse = lifeProposal.isNonFinancialEndorse();
//		this.specialDiscount = lifeProposal.getSpecialDiscount();
		this.currencyRate = lifeProposal.getCurrencyRate();
		for (ProposalInsuredPerson person : lifeProposal.getProposalInsuredPersonList()) {
			addInsuredPerson(new PolicyInsuredPerson(person));
		}

		for (LifeProposalAttachment attach : lifeProposal.getAttachmentList()) {
			addLifePolicyAttachment(new LifePolicyAttachment(attach));
		}
		for (Attachment medicalAttach : lifeProposal.getCustomerMedicalCheckUpAttachmentList()) {
			addCustomerMedicalChkUpAttachment(new Attachment(medicalAttach));
		}
	}

	public LifePolicy(LifePolicyHistory history) {
		this.id = history.getPolicyReferenceNo();
		this.isCoinsuranceApplied = history.isCoinsuranceApplied();
		this.isEndorsementApplied = history.isActiveEndorsement();
		this.isNonFinancialEndorse = history.isNonFinancialEndorse();
		this.lastPaymentTerm = history.getLastPaymentTerm();
		this.periodMonth = history.getPeriodMonth();
		this.printCount = history.getPrintCount();
		this.totalDiscountAmount = history.getTotalDiscountAmount();
		this.standardExcess = history.getStandardExcess();
		this.policyNo = history.getPolicyNo();
		this.endorsementNo = history.getEndorsementNo();
		this.commenmanceDate = history.getCommenmanceDate();
		this.activedPolicyStartDate = history.getActivedPolicyStartDate();
		this.activedPolicyEndDate = history.getActivedPolicyEndDate();
		this.coverageDate = history.getCoverageDate();
		this.issueDate = history.getIssueDate();
		this.endorsementConfirmDate = history.getEndorsementConfirmDate();
		this.policyStatus = history.getPolicyStatus();
		this.customer = history.getCustomer();
		this.organization = history.getOrganization();
		this.approvedBy = history.getApprovedBy();
		this.branch = history.getBranch();
		this.salesPoints = history.getSalesPoints();
		this.paymentType = history.getPaymentType();
		this.agent = history.getAgent();
		this.saleBank = history.getSaleBank();
		this.saleChannelType = history.getSaleChannelType();
		this.lifeProposal = history.getLifeProposal();
		this.currencyRate = history.getCurrencyRate();

//		for (PolicyInsuredPersonHistory iPerson : history.getPolicyInsuredPersonList()) {
//			addInsuredPerson(new PolicyInsuredPerson(iPerson));
//		}

		for (LifePolicyAttachmentHistory attachment : history.getAttachmentList()) {
			addLifePolicyAttachment(new LifePolicyAttachment(attachment));
		}
	}

	public void addInsuredPerson(PolicyInsuredPerson policyInsuredPerson) {
		policyInsuredPerson.setLifePolicy(this);
		getPolicyInsuredPersonList().add(policyInsuredPerson);
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public User getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(User approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getCommenmanceDate() {
		return commenmanceDate;
	}

	public void setCommenmanceDate(Date commenmanceDate) {
		this.commenmanceDate = commenmanceDate;
	}

	public Date getActivedPolicyStartDate() {
		return activedPolicyStartDate;
	}

	public void setActivedPolicyStartDate(Date activedPolicyStartDate) {
		this.activedPolicyStartDate = activedPolicyStartDate;
	}

	public Date getActivedPolicyEndDate() {
		return activedPolicyEndDate;
	}

	public void setActivedPolicyEndDate(Date activedPolicyEndDate) {
		this.activedPolicyEndDate = activedPolicyEndDate;
	}

	public int getPeriodMonth() {
		return periodMonth;
	}

	public void setPeriodMonth(int periodMonth) {
		this.periodMonth = periodMonth;
	}

	public int getPeriodOfMonths() {
		if (KeyFactorChecker.isPersonalAccident(policyInsuredPersonList.get(0).getProduct())) {
			return periodMonth;
		} else {
			return periodMonth * 12;
		}
	}

	public int getPeriodOfInsurance() {
		if (KeyFactorChecker.isPersonalAccident(policyInsuredPersonList.get(0).getProduct())) {
			return periodMonth;
		} else {
			return periodMonth / 12;
		}
	}

	public int getPeriodOfYears() {
		return periodMonth / 12;
	}

	public List<Date> getTimeSlotList() {
		List<Date> result = new ArrayList<Date>();
		result.add(activedPolicyStartDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(activedPolicyStartDate);
		int months = paymentType.getMonth();
		if (months > 0) {
			int a = 12 / months;
			for (int i = 1; i < a; i++) {
				cal.add(Calendar.MONTH, months);
				result.add(cal.getTime());
			}
		}
		return result;
	}

	public Date getLastPaymentDate() {
		List<Date> dateList = getTimeSlotList();
		dateList.add(activedPolicyStartDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(activedPolicyStartDate);
		int months = paymentType.getMonth();
		if (months > 0) {
			int a = getPeriodMonth() / months;
			for (int i = 1; i < a; i++) {
				cal.add(Calendar.MONTH, months);
				dateList.add(cal.getTime());
			}
		}

		if (!dateList.isEmpty()) {
			int lastIndex = dateList.size() - 1;
			return dateList.get(lastIndex);
		}
		return null;
	}

	public int getPaymentTimes() {
		int monthTimes = paymentType.getMonth();
		if (monthTimes > 0) {
			return periodMonth / monthTimes;
		} else {
			return 1;
		}
	}

	public Date getCoverageDate() {
		return coverageDate;
	}

	public void setCoverageDate(Date coverageDate) {
		this.coverageDate = coverageDate;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public double getTotalDiscountAmount() {
		return totalDiscountAmount;
	}

	public void setTotalDiscountAmount(double totalDiscountAmount) {
		this.totalDiscountAmount = totalDiscountAmount;
	}

	public double getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(double currencyRate) {
		this.currencyRate = currencyRate;
	}

	public String getCustomerEmail() {
		if (customer != null) {
			return customer.getContentInfo().getEmail();
		}
		if (organization != null) {
			return organization.getContentInfo().getEmail();
		}
		return null;
	}

	public List<PolicyInsuredPerson> getInsuredPersonInfo() {
		if (policyInsuredPersonList == null) {
			policyInsuredPersonList = new ArrayList<PolicyInsuredPerson>();
		} else {
			List<PolicyInsuredPerson> tempList = new ArrayList<PolicyInsuredPerson>();
			tempList.addAll(policyInsuredPersonList);
			for (PolicyInsuredPerson policyInsuredPerson : policyInsuredPersonList) {
				if (EndorsementStatus.TERMINATE.equals(policyInsuredPerson.getEndorsementStatus())) {
					tempList.remove(policyInsuredPerson);
				}
			}
			policyInsuredPersonList.clear();
			policyInsuredPersonList.addAll(tempList);
		}
		return policyInsuredPersonList;
	}

	public void setVehicleInfo(List<PolicyInsuredPerson> insuredPersonInfo) {
		this.policyInsuredPersonList = insuredPersonInfo;
	}

	public LifeProposal getLifeProposal() {
		return lifeProposal;
	}

	public void setLifeProposal(LifeProposal lifeProposal) {
		this.lifeProposal = lifeProposal;
	}

	public List<LifePolicyAttachment> getAttachmentList() {
		if (attachmentList == null) {
			attachmentList = new ArrayList<LifePolicyAttachment>();
		}
		return attachmentList;
	}

	public void setAttachmentList(List<LifePolicyAttachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public SalesPoints getSalesPoints() {
		return salesPoints;
	}

	public void setSalesPoints(SalesPoints salesPoints) {
		this.salesPoints = salesPoints;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public SaleChannelType getSaleChannelType() {
		return saleChannelType;
	}

	public void setSaleChannelType(SaleChannelType saleChannelType) {
		this.saleChannelType = saleChannelType;
	}

	public BankBranch getSaleBank() {
		return saleBank;
	}

	public void setSaleBank(BankBranch saleBank) {
		this.saleBank = saleBank;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public double getStandardExcess() {
		return standardExcess;
	}

	public void setStandardExcess(double standardExcess) {
		this.standardExcess = standardExcess;
	}

	public boolean isNonFinancialEndorse() {
		return isNonFinancialEndorse;
	}

	public void setNonFinancialEndorse(boolean isNonFinancialEndorse) {
		this.isNonFinancialEndorse = isNonFinancialEndorse;
	}

	public int getPrintCount() {
		return printCount;
	}

	public void setPrintCount(int printCount) {
		this.printCount = printCount;
	}

	public double getSpecialDiscount() {
		return specialDiscount;
	}

	public void setSpecialDiscount(double specialDiscount) {
		this.specialDiscount = specialDiscount;
	}

	public double getSpecialDiscountAmount() {
		double specialDiscountAmount = Utils.getPercentOf(specialDiscount, getPremium());
		return Utils.getTwoDecimalPoint(specialDiscountAmount);
	}

	public List<PolicyInsuredPerson> getPolicyInsuredPersonList() {
		if (policyInsuredPersonList == null) {
			policyInsuredPersonList = new ArrayList<PolicyInsuredPerson>();
		}
		return policyInsuredPersonList;
	}

	public void setPolicyInsuredPersonList(List<PolicyInsuredPerson> policyInsuredPersonList) {
		this.policyInsuredPersonList = policyInsuredPersonList;
	}

	public void addPolicyInsuredPersonInfo(PolicyInsuredPerson policyInsuredPersonInfo) {
		if (policyInsuredPersonList == null) {
			policyInsuredPersonList = new ArrayList<PolicyInsuredPerson>();
		}
		policyInsuredPersonInfo.setLifePolicy(this);
		policyInsuredPersonList.add(policyInsuredPersonInfo);
	}

	public void addLifePolicyAttachment(LifePolicyAttachment attachment) {
		attachment.setLifePolicy(this);
		getAttachmentList().add(attachment);
	}

	public void addCustomerMedicalChkUpAttachment(Attachment attachment) {
		if (customerMedicalCheckUpAttachmentList == null) {
			customerMedicalCheckUpAttachmentList = new ArrayList<Attachment>();
		}
		getCustomerMedicalCheckUpAttachmentList().add(attachment);

	}

	public String getProposalNo() {
		return lifeProposal.getProposalNo();
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public boolean isCoinsuranceApplied() {
		return isCoinsuranceApplied;
	}

	public void setCoinsuranceApplied(boolean isCoinsuranceApplied) {
		this.isCoinsuranceApplied = isCoinsuranceApplied;
	}

	public boolean isEndorsementApplied() {
		return isEndorsementApplied;
	}

	public void setEndorsementApplied(boolean isEndorsementApplied) {
		this.isEndorsementApplied = isEndorsementApplied;
	}

	public String getEndorsementNo() {
		return endorsementNo;
	}

	public void setEndorsementNo(String endorsementNo) {
		this.endorsementNo = endorsementNo;
	}

	public boolean isDelFlag() {
		return delFlag;
	}

	public void setDelFlag(boolean delFlag) {
		this.delFlag = delFlag;
	}

	public int getLastPaymentTerm() {
		return lastPaymentTerm;
	}

	public void setLastPaymentTerm(int lastPaymentTerm) {
		this.lastPaymentTerm = lastPaymentTerm;
	}

	public PolicyStatus getPolicyStatus() {
		return policyStatus;
	}

	public void setPolicyStatus(PolicyStatus policyStatus) {
		this.policyStatus = policyStatus;
	}

	public Date getEndorsementConfirmDate() {
		return endorsementConfirmDate;
	}

	public void setEndorsementConfirmDate(Date endorsementConfirmDate) {
		this.endorsementConfirmDate = endorsementConfirmDate;
	}

	public Date getRenewalConfirmDate() {
		return renewalConfirmDate;
	}

	public void setRenewalConfirmDate(Date renewalConfirmDate) {
		this.renewalConfirmDate = renewalConfirmDate;
	}

	/* System Auto Calculate Process */
	public double getPremium() {
		double premium = 0.0;
		for (PolicyInsuredPerson pi : policyInsuredPersonList) {
			if (!EndorsementStatus.TERMINATE.equals(pi.getEndorsementStatus())) {
				premium = Utils.getTwoDecimalPoint(premium + (pi.getPremium() + pi.getAddOnTermPremium()));
			}
		}
		return premium;
	}

	public double getAddOnPremium() {
		double premium = 0.0;
		for (PolicyInsuredPerson pi : policyInsuredPersonList) {
			premium = Utils.getTwoDecimalPoint(premium + pi.getAddOnPremium());
		}
		return premium;
	}

	public double getSumInsured() {
		double sumInsured = 0.0;
		for (PolicyInsuredPerson pi : policyInsuredPersonList) {
			sumInsured = sumInsured + pi.getSumInsured();
		}
		return sumInsured;
	}

	public double getSuminsuredPerUnit() {
		double siPerUnit = 0.0;
		for (PolicyInsuredPerson pi : policyInsuredPersonList) {
			if (pi.getUnit() > 0) {
				siPerUnit = siPerUnit + pi.getSuminsuredPerUnit();
			}
		}
		return siPerUnit;
	}

	public double getAddOnSumInsured() {
		double sumInsured = 0.0;
		for (PolicyInsuredPerson pi : policyInsuredPersonList) {
			sumInsured = sumInsured + pi.getAddOnSumInsure();
		}
		return sumInsured;
	}

	public double getTotalPremium() {
		return getPremium() + getAddOnPremium();
	}

	public double getNetPremium() {
		double netPremium = getTotalPremium() - getSpecialDiscountAmount();
		return Utils.getTwoDecimalPoint(netPremium);
	}

	public double getTotalSumInsured() {
		return getSumInsured() + getAddOnSumInsured();
	}

	public String getTotalSumInsuredString() {
		return Utils.getCurrencyFormatString(getSumInsured() + getAddOnSumInsured());
	}

	public double getTotalTermPremium() {
		return Utils.getTwoDecimalPoint(getTotalBasicTermPremium() + getTotalAddOnTermPremium());
	}

	public double getTotalBasicTermPremium() {
		double termPermium = 0.0;
		for (PolicyInsuredPerson pi : policyInsuredPersonList) {
			termPermium = Utils.getTwoDecimalPoint(termPermium + pi.getBasicTermPremium());
		}
		return termPermium;
	}

	public String getTotalBasicTermPremiumString() {
		double termPermium = 0.0;
		for (PolicyInsuredPerson pi : policyInsuredPersonList) {
			termPermium = Utils.getTwoDecimalPoint(termPermium + pi.getBasicTermPremium());
		}
		return Utils.getCurrencyFormatString(termPermium);
	}

	public double getTotalAddOnTermPremium() {
		double termPermium = 0.0;
		for (PolicyInsuredPerson pi : policyInsuredPersonList) {
			termPermium = Utils.getTwoDecimalPoint(termPermium + pi.getAddOnTermPremium());
		}
		return termPermium;
	}

	public double getEndorsementBasicPremium() {
		double premium = 0.0;
		for (PolicyInsuredPerson pi : policyInsuredPersonList) {
			premium = Utils.getTwoDecimalPoint(premium + pi.getEndorsementNetBasicPremium());
		}
		return premium;
	}

	public double getEndorsementAddOnPremium() {
		double premium = 0.0;
		for (PolicyInsuredPerson pi : policyInsuredPersonList) {
			premium = Utils.getTwoDecimalPoint(premium + pi.getEndorsementNetAddonPremium());
		}
		return premium;
	}

	public double getTotalEndorsementPremium() {
		return Utils.getTwoDecimalPoint(getEndorsementBasicPremium() + getEndorsementAddOnPremium());
	}

	public double getAgentCommission() {
		double totalCommission = 0.0;
		if (agent != null) {
			for (PolicyInsuredPerson pip : policyInsuredPersonList) {
				double commissionPercent = pip.getProduct().getFirstCommission();
				if (commissionPercent > 0) {
//					double totalPremium = pip.getTotalTermPermium();
//					double commission = Utils.getPercentOf(commissionPercent, totalPremium);
//					totalCommission = totalCommission + commission;
				}
			}
		}
		return Utils.getTwoDecimalPoint(totalCommission);
	}

	public double getRenewalAgentCommission() {
		double totalCommission = 0.0;
		if (agent != null) {
			for (PolicyInsuredPerson pip : policyInsuredPersonList) {
				double commissionPercent = pip.getProduct().getRenewalCommission();
				if (commissionPercent > 0) {
//					double totalPremium = pip.getTotalTermPermium();
//					double commission = Utils.getPercentOf(commissionPercent, totalPremium);
//					totalCommission = totalCommission + commission;
				}
			}
		}
		return Utils.getTwoDecimalPoint(totalCommission);
	}

	public String getCustomerId() {
		if (customer != null) {
			return customer.getId();
		}
		if (organization != null) {
			return organization.getId();
		}
		return null;
	}

	public String getCustomerName() {
		if (customer != null) {
			return customer.getFullName();
		}
		if (organization != null) {
			return organization.getName();
		}
		return null;
	}

	public String getCustomerNRC() {
		if (customer != null) {
			return customer.getFullIdNoForView();
		}
		return null;
	}

	public String getOrganizationName() {
		if (customer != null) {
			return customer.getFullName();
		}
		if (organization != null) {
			return organization.getName();
		}
		return null;
	}

	public String getOwnerName() {
		if (customer != null) {
			return customer.getFullName();
		}
		if (organization != null) {
			return organization.getOwnerNameForView();
		}
		return null;
	}

	public String getCustomerAddress() {
		if (customer != null) {
			return customer.getFullAddress();
		}
		if (organization != null) {
			return organization.getFullAddress();
		}
		return null;
	}

	public String getCustomerPhoneNo() {
		if (customer != null) {
			return customer.getContentInfo().getPhoneOrMoblieNo();
		}
		if (organization != null) {
			return organization.getContentInfo().getPhoneOrMoblieNo();
		}
		return null;
	}

	public String getAgentName() {
		if (agent != null)
			return agent.getFullName();
		else
			return "N/A";
	}

	public String getAgentLiscenseNo() {
		if (agent != null)
			return agent.getLiscenseNo();
		else
			return "[0]";
	}

	public String getAgentNameNLiscenseNo() {
		if (agent != null)
			return agent.getFullName() + " [" + agent.getLiscenseNo() + "]";
		else
			return "N/A[0]";
	}

	public ProductGroup getProductGroup() {
		if (this.policyInsuredPersonList != null && !this.policyInsuredPersonList.isEmpty()) {
			return this.policyInsuredPersonList.get(0).getProduct().getProductGroup();
		}
		return null;
	}

	/**
	 * @see org.ace.insurance.common.interfaces.IPolicy#getInsuredItems()
	 */
	public List<IInsuredItem> getInsuredItems() {
		List<IInsuredItem> insuredItems = Collections.emptyList();
		List<PolicyInsuredPerson> personList = getPolicyInsuredPersonList();
		if (personList != null) {
			insuredItems = new ArrayList<IInsuredItem>();
			for (PolicyInsuredPerson person : personList) {
				insuredItems.add(person);
			}
		}
		return insuredItems;
	}

	public String getSalePersonName() {
		if (agent != null) {
			return agent.getFullName();
		} else if (saleBank != null) {
			return saleBank.getName();
		}
		return null;
	}

	public InsuranceType getInsuranceType() {
		return InsuranceType.LIFE;
	}

	public String getSaleChannel() {
		if (agent != null) {
			return agent.getFullName();
		} else if (saleBank != null) {
			return saleBank.getName();
		} else if (saleChannelType.equals(SaleChannelType.WALKIN)) {
			return SaleChannelType.WALKIN.toString();
		} else if (saleChannelType.equals(SaleChannelType.DIRECTMARKETING)) {
			return SaleChannelType.DIRECTMARKETING.toString();
		}
		return null;
	}

	public double getTotalTermDiscountAmount() {
		double specialDiscountAmount = Utils.getPercentOf(specialDiscount, getTotalTermPremium());
		return Utils.getTwoDecimalPoint(specialDiscountAmount);
	}

	public double getNetTermPremium() {
		return getTotalTermPremium() - getTotalTermDiscountAmount();
	}

	@Override
	public String getRegistrationNo() {
		return policyNo;
	}

	public String agentNameWithLiscenceNo() {
		if (agent != null) {
			return agent.getFullName() + "( " + agent.getLiscenseNo() + " )";
		}
		return "";
	}

	public List<Attachment> getCustomerMedicalCheckUpAttachmentList() {
		return customerMedicalCheckUpAttachmentList;
	}

	public void setCustomerMedicalCheckUpAttachmentList(List<Attachment> customerMedicalCheckUpAttachmentList) {
		this.customerMedicalCheckUpAttachmentList = customerMedicalCheckUpAttachmentList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activedPolicyEndDate == null) ? 0 : activedPolicyEndDate.hashCode());
		result = prime * result + ((activedPolicyStartDate == null) ? 0 : activedPolicyStartDate.hashCode());
		result = prime * result + ((agent == null) ? 0 : agent.hashCode());
		result = prime * result + ((approvedBy == null) ? 0 : approvedBy.hashCode());
		result = prime * result + ((attachmentList == null) ? 0 : attachmentList.hashCode());
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((commenmanceDate == null) ? 0 : commenmanceDate.hashCode());
		result = prime * result + ((coverageDate == null) ? 0 : coverageDate.hashCode());
		long temp;
		temp = Double.doubleToLongBits(currencyRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + (delFlag ? 1231 : 1237);
		result = prime * result + ((endorsementConfirmDate == null) ? 0 : endorsementConfirmDate.hashCode());
		result = prime * result + ((endorsementNo == null) ? 0 : endorsementNo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isCoinsuranceApplied ? 1231 : 1237);
		result = prime * result + (isEndorsementApplied ? 1231 : 1237);
		result = prime * result + (isNonFinancialEndorse ? 1231 : 1237);
		result = prime * result + lastPaymentTerm;
		result = prime * result + ((organization == null) ? 0 : organization.hashCode());
		result = prime * result + ((paymentType == null) ? 0 : paymentType.hashCode());
		result = prime * result + periodMonth;
		result = prime * result + ((policyNo == null) ? 0 : policyNo.hashCode());
		result = prime * result + ((policyStatus == null) ? 0 : policyStatus.hashCode());
		result = prime * result + printCount;
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((renewalConfirmDate == null) ? 0 : renewalConfirmDate.hashCode());
		result = prime * result + ((saleBank == null) ? 0 : saleBank.hashCode());
		result = prime * result + ((saleChannelType == null) ? 0 : saleChannelType.hashCode());
		temp = Double.doubleToLongBits(specialDiscount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(standardExcess);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(totalDiscountAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		LifePolicy other = (LifePolicy) obj;
		if (activedPolicyEndDate == null) {
			if (other.activedPolicyEndDate != null)
				return false;
		} else if (!activedPolicyEndDate.equals(other.activedPolicyEndDate))
			return false;
		if (activedPolicyStartDate == null) {
			if (other.activedPolicyStartDate != null)
				return false;
		} else if (!activedPolicyStartDate.equals(other.activedPolicyStartDate))
			return false;
		if (agent == null) {
			if (other.agent != null)
				return false;
		} else if (!agent.equals(other.agent))
			return false;
		if (approvedBy == null) {
			if (other.approvedBy != null)
				return false;
		} else if (!approvedBy.equals(other.approvedBy))
			return false;
		if (attachmentList == null) {
			if (other.attachmentList != null)
				return false;
		} else if (!attachmentList.equals(other.attachmentList))
			return false;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (commenmanceDate == null) {
			if (other.commenmanceDate != null)
				return false;
		} else if (!commenmanceDate.equals(other.commenmanceDate))
			return false;
		if (coverageDate == null) {
			if (other.coverageDate != null)
				return false;
		} else if (!coverageDate.equals(other.coverageDate))
			return false;
		if (Double.doubleToLongBits(currencyRate) != Double.doubleToLongBits(other.currencyRate))
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (delFlag != other.delFlag)
			return false;
		if (endorsementConfirmDate == null) {
			if (other.endorsementConfirmDate != null)
				return false;
		} else if (!endorsementConfirmDate.equals(other.endorsementConfirmDate))
			return false;
		if (endorsementNo == null) {
			if (other.endorsementNo != null)
				return false;
		} else if (!endorsementNo.equals(other.endorsementNo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isCoinsuranceApplied != other.isCoinsuranceApplied)
			return false;
		if (isEndorsementApplied != other.isEndorsementApplied)
			return false;
		if (isNonFinancialEndorse != other.isNonFinancialEndorse)
			return false;
		if (lastPaymentTerm != other.lastPaymentTerm)
			return false;
		if (lifeProposal == null) {
			if (other.lifeProposal != null)
				return false;
		} else if (!lifeProposal.equals(other.lifeProposal))
			return false;
		if (organization == null) {
			if (other.organization != null)
				return false;
		} else if (!organization.equals(other.organization))
			return false;
		if (paymentType == null) {
			if (other.paymentType != null)
				return false;
		} else if (!paymentType.equals(other.paymentType))
			return false;
		if (periodMonth != other.periodMonth)
			return false;
		if (policyNo == null) {
			if (other.policyNo != null)
				return false;
		} else if (!policyNo.equals(other.policyNo))
			return false;
		if (policyStatus != other.policyStatus)
			return false;
		if (printCount != other.printCount)
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (renewalConfirmDate == null) {
			if (other.renewalConfirmDate != null)
				return false;
		} else if (!renewalConfirmDate.equals(other.renewalConfirmDate))
			return false;
		if (saleBank == null) {
			if (other.saleBank != null)
				return false;
		} else if (!saleBank.equals(other.saleBank))
			return false;
		if (saleChannelType != other.saleChannelType)
			return false;
		if (Double.doubleToLongBits(specialDiscount) != Double.doubleToLongBits(other.specialDiscount))
			return false;
		if (Double.doubleToLongBits(standardExcess) != Double.doubleToLongBits(other.standardExcess))
			return false;
		if (Double.doubleToLongBits(totalDiscountAmount) != Double.doubleToLongBits(other.totalDiscountAmount))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	@Override
	public ProposalType getProposalType() {
		return null;
	}

	@Override
	public int getTotalUnit() {
		return 0;
	}

	public String getPeriod() {
		if (periodMonth / 12 < 1) {
			return periodMonth + " - Months";
		} else {
			return periodMonth / 12 + " - Year";
		}
	}

	public int getTotalPaymentTimes() {
		if (paymentType.getMonth() == 0) {
			return 1;
		} else {
			int totalPaymentTimes = 0;
			int paymentMonths = 0;
			if (periodMonth > paymentMonths) {
				paymentMonths = periodMonth;
			}
			totalPaymentTimes = paymentMonths / paymentType.getMonth();
			return totalPaymentTimes;
		}
	}

	public String getTimeSlotListStr() {
		StringBuffer buffer = new StringBuffer();
		Calendar cal = Calendar.getInstance();
		cal.setTime(activedPolicyStartDate);
		int months = paymentType.getMonth();
		if (months == 1) {
			buffer.append("( ");
			buffer.append(Utils.formattedDate(cal.getTime(), "dd"));
			buffer.append(" )");
		} else if (months > 0 && months != 12) {
			buffer.append(Utils.formattedDate(cal.getTime(), "MMMM"));
			buffer.append(" ");
			int a = 12 / months;
			for (int i = 1; i < a; i++) {
				cal.add(Calendar.MONTH, months);
				buffer.append(Utils.formattedDate(cal.getTime(), "MMMM"));
				buffer.append(" ");
			}
			buffer.append("- ( ");
			buffer.append(Utils.formattedDate(cal.getTime(), "dd"));
			buffer.append(" )");
		} else if (months == 12) {
			buffer.append(Utils.formattedDate(cal.getTime(), "MMMM"));
			buffer.append(" ( ");
			buffer.append(Utils.formattedDate(cal.getTime(), "dd"));
			buffer.append(" )");
		}
		return buffer.toString();
	}

	public Date abstractDatyByPaymentType() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(activedPolicyEndDate);
		int paymentMonth = paymentType.getMonth();
		int modulusMont = paymentMonth % 12;
		if (modulusMont == 0) {
			int year = cal.get(Calendar.YEAR) - 1;
			cal.set(Calendar.YEAR, year);
		} else {
			int month = cal.get(Calendar.MONTH) - modulusMont;
			cal.set(Calendar.MONTH, month);
		}
		return cal.getTime();
	}
}
