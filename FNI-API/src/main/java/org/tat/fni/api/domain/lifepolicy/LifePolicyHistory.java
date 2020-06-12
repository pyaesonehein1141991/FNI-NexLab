package org.tat.fni.api.domain.lifepolicy;

import java.io.Serializable;
import java.util.ArrayList;
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
import org.tat.fni.api.common.emumdata.PolicyHistoryEntryType;
import org.tat.fni.api.common.emumdata.SaleChannelType;
import org.tat.fni.api.domain.Agent;
import org.tat.fni.api.domain.BankBranch;
import org.tat.fni.api.domain.Branch;
import org.tat.fni.api.domain.Customer;
import org.tat.fni.api.domain.Organization;
import org.tat.fni.api.domain.PaymentType;
import org.tat.fni.api.domain.PolicyStatus;
import org.tat.fni.api.domain.ProductGroup;
import org.tat.fni.api.domain.SalesPoints;
import org.tat.fni.api.domain.User;
import org.tat.fni.api.domain.lifeproposal.LifeProposal;


@Entity
@Table(name = TableName.LIFEPOLICYHISTORY)
@TableGenerator(name = "LIFEPOLICYHISTORY_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LIFEPOLICYHISTORY_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "LifePolicyHistory.findAll", query = "SELECT l FROM LifePolicyHistory l "),
		@NamedQuery(name = "LifePolicyHistory.findAllActiveLifePolicy", query = "SELECT l FROM LifePolicyHistory l WHERE l.policyNo IS NOT NULL"),
		@NamedQuery(name = "LifePolicyHistory.findByProposalId", query = "SELECT l FROM LifePolicyHistory l WHERE l.lifeProposal.id = :lifeProposalId"),
		@NamedQuery(name = "LifePolicyHistory.findById", query = "SELECT l FROM LifePolicyHistory l WHERE l.id = :id"),
		// @NamedQuery(name = "LifePolicyHistory.findForCoinsurance", query =
		// "SELECT l FROM MotorPolicy l WHERE l.commenmanceDate IS NOT NULL AND
		// l.isCoinsuranceApplied = FALSE AND l.totalSumInsured >
		// l.productGroup.maxSumInsured"),
		@NamedQuery(name = "LifePolicyHistory.increasePrintCount", query = "UPDATE LifePolicyHistory l SET l.printCount = l.printCount + 1 WHERE l.id = :id"),
		@NamedQuery(name = "LifePolicyHistory.findByPolicyNo", query = "SELECT l FROM LifePolicyHistory l where l.policyNo = :policyNo "),
		@NamedQuery(name = "LifePolicyHistory.updateCommenmanceDate", query = "UPDATE LifePolicyHistory l SET l.commenmanceDate = :commenceDate WHERE l.id = :id") })
@EntityListeners(IDInterceptor.class)
public class LifePolicyHistory implements Serializable {
	private static final long serialVersionUID = -4188881327890797521L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LIFEPOLICYHISTORY_GEN")
	private String id;

	private boolean isCoinsuranceApplied;
	private boolean activeEndorsement;
	private boolean isNonFinancialEndorse;
	private int lastPaymentTerm;
	private int printCount;
	@Column(name = "PERIODOFMONTH")
	private int periodMonth;
	@Column(name = "ENTRY_COUNT")
	private int entryCount;
	private double totalDiscountAmount;
	private double standardExcess;
	private String policyNo;
	private String policyReferenceNo;
	private String endorsementNo;
	private double currencyRate;

	@Enumerated(EnumType.STRING)
	private PolicyStatus policyStatus;

	@Enumerated(EnumType.STRING)
	@Column(name = "ENTRY_TYPE")
	private PolicyHistoryEntryType entryType;

	@Column(name = "ENDORSEMENTCONFIRMDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endorsementConfirmDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date commenmanceDate;

	@Column(name = "RENEWALCONFIRMDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date renewalConfirmDate;

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
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMERID", referencedColumnName = "ID")
	private Customer customer;

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

	@Enumerated(EnumType.STRING)
	private SaleChannelType saleChannelType;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SALEBANKID", referencedColumnName = "ID")
	private BankBranch saleBank;

	@OneToOne
	@JoinColumn(name = "PROPOSALID")
	private LifeProposal lifeProposal;

//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "lifePolicy", orphanRemoval = true)
//	private List<PolicyInsuredPersonHistory> policyInsuredPersonList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "lifePolicy", orphanRemoval = true)
	private List<LifePolicyAttachmentHistory> attachmentList;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public LifePolicyHistory() {
	}

	public LifePolicyHistory(LifePolicy lifePolicy) {
		this.customer = lifePolicy.getCustomer();
		this.organization = lifePolicy.getOrganization();
		this.branch = lifePolicy.getBranch();
		this.salesPoints = lifePolicy.getSalesPoints();
		this.paymentType = lifePolicy.getPaymentType();
		this.saleBank = lifePolicy.getSaleBank();
		this.saleChannelType = lifePolicy.getSaleChannelType();
		this.agent = lifePolicy.getAgent();
		this.lifeProposal = lifePolicy.getLifeProposal();
		this.policyNo = lifePolicy.getPolicyNo();
		this.commenmanceDate = lifePolicy.getCommenmanceDate();
		this.isNonFinancialEndorse = lifePolicy.isNonFinancialEndorse();
		this.currencyRate = lifePolicy.getCurrencyRate();
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

//	public List<PolicyInsuredPersonHistory> getInsuredPersonInfo() {
//		return policyInsuredPersonList;
//	}
//
//	public void setVehicleInfo(List<PolicyInsuredPersonHistory> insuredPersonInfo) {
//		this.policyInsuredPersonList = insuredPersonInfo;
//	}

	public LifeProposal getLifeProposal() {
		return lifeProposal;
	}

	public void setLifeProposal(LifeProposal lifeProposal) {
		this.lifeProposal = lifeProposal;
	}

	public List<LifePolicyAttachmentHistory> getAttachmentList() {
		if (this.attachmentList == null) {
			this.attachmentList = new ArrayList<LifePolicyAttachmentHistory>();
		}
		return this.attachmentList;
	}

	public void setAttachmentList(List<LifePolicyAttachmentHistory> attachmentList) {
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

//	public List<PolicyInsuredPersonHistory> getPolicyInsuredPersonList() {
//		if (this.policyInsuredPersonList == null) {
//			this.policyInsuredPersonList = new ArrayList<PolicyInsuredPersonHistory>();
//		}
//		return policyInsuredPersonList;
//	}
//
//	public void setPolicyInsuredPersonList(List<PolicyInsuredPersonHistory> policyInsuredPersonList) {
//		this.policyInsuredPersonList = policyInsuredPersonList;
//	}
//
//	public void addPolicyInsuredPersonInfo(PolicyInsuredPersonHistory policyInsuredPersonInfo) {
//		if (policyInsuredPersonList == null) {
//			policyInsuredPersonList = new ArrayList<PolicyInsuredPersonHistory>();
//		}
//		policyInsuredPersonInfo.setLifePolicy(this);
//		policyInsuredPersonList.add(policyInsuredPersonInfo);
//	}

	public void addLifePolicyAttachment(LifePolicyAttachmentHistory attachment) {
		if (attachmentList == null) {
			attachmentList = new ArrayList<LifePolicyAttachmentHistory>();
		}
		attachment.setLifePolicy(this);
		attachmentList.add(attachment);
	}

	/* System Auto Calculate Process */
//	public double getPremium() {
//		double premium = 0.0;
//		for (PolicyInsuredPersonHistory pi : policyInsuredPersonList) {
//			premium = premium + pi.getPremium();
//		}
//		return premium;
//	}

//	public double getAddOnPremium() {
//		double premium = 0.0;
//		for (PolicyInsuredPersonHistory pi : policyInsuredPersonList) {
//			premium = premium + pi.getAddOnPremium();
//		}
//		return premium;
//	}
//
//	public double getSumInsured() {
//		double sumInsured = 0.0;
//		for (PolicyInsuredPersonHistory pi : policyInsuredPersonList) {
//			sumInsured = sumInsured + pi.getSumInsured();
//		}
//		return sumInsured;
//	}
//
//	public double getAddOnSumInsured() {
//		double sumInsured = 0.0;
//		for (PolicyInsuredPersonHistory pi : policyInsuredPersonList) {
//			sumInsured = sumInsured + pi.getAddOnSumInsure();
//		}
//		return sumInsured;
//	}

//	public double getTotalPremium() {
//		return getPremium() + getAddOnPremium();
//	}
//
//	public double getTotalSumInsured() {
//		return getSumInsured() + getAddOnSumInsured();
//	}

//	public double getTotalTermPremium() {
//		return getTotalBasicTermPremium() + getTotalAddOnTermPremium();
//	}

//	public double getTotalBasicTermPremium() {
//		double termPermium = 0.0;
//		for (PolicyInsuredPersonHistory pi : policyInsuredPersonList) {
//			termPermium = termPermium + pi.getBasicTermPremium();
//		}
//		return termPermium;
//	}
//
//	public double getTotalAddOnTermPremium() {
//		double termPermium = 0.0;
//		for (PolicyInsuredPersonHistory pi : policyInsuredPersonList) {
//			termPermium = termPermium + pi.getAddOnTermPremium();
//		}
//		return termPermium;
//	}

	public String getCustomerName() {
		if (customer != null) {
			return customer.getFullName();
		}
		if (organization != null) {
			return organization.getOwnerName();
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

//	public ProductGroup getProductGroup() {
//		if (this.policyInsuredPersonList != null && !this.policyInsuredPersonList.isEmpty()) {
//			return this.policyInsuredPersonList.get(0).getProduct().getProductGroup();
//		}
//		return null;
//	}

	public boolean isActiveEndorsement() {
		return activeEndorsement;
	}

	public void setActiveEndorsement(boolean activeEndorsement) {
		this.activeEndorsement = activeEndorsement;
	}

	public Date getEndorsementConfirmDate() {
		return endorsementConfirmDate;
	}

	public void setEndorsementConfirmDate(Date endorsementConfirmDate) {
		this.endorsementConfirmDate = endorsementConfirmDate;
	}

	public PolicyStatus getPolicyStatus() {
		return policyStatus;
	}

	public void setPolicyStatus(PolicyStatus policyStatus) {
		this.policyStatus = policyStatus;
	}

	public String getPolicyReferenceNo() {
		return policyReferenceNo;
	}

	public void setPolicyReferenceNo(String policyReferenceNo) {
		this.policyReferenceNo = policyReferenceNo;
	}

	public int getEntryCount() {
		return entryCount;
	}

	public void setEntryCount(int entryCount) {
		this.entryCount = entryCount;
	}

	public PolicyHistoryEntryType getEntryType() {
		return entryType;
	}

	public void setEntryType(PolicyHistoryEntryType entryType) {
		this.entryType = entryType;
	}

	public Date getRenewalConfirmDate() {
		return renewalConfirmDate;
	}

	public void setRenewalConfirmDate(Date renewalConfirmDate) {
		this.renewalConfirmDate = renewalConfirmDate;
	}

	public int getLastPaymentTerm() {
		return lastPaymentTerm;
	}

	public void setLastPaymentTerm(int lastPaymentTerm) {
		this.lastPaymentTerm = lastPaymentTerm;
	}

	public int getPeriodMonth() {
		return periodMonth;
	}

	public void setPeriodMonth(int periodMonth) {
		this.periodMonth = periodMonth;
	}

//	public int getPeriodOfInsurance() {
//		if (KeyFactorChecker.isPersonalAccident(policyInsuredPersonList.get(0).getProduct())) {
//			return periodMonth;
//		} else {
//			return periodMonth / 12;
//		}
//	}

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

	public String getEndorsementNo() {
		return endorsementNo;
	}

	public void setEndorsementNo(String endorsementNo) {
		this.endorsementNo = endorsementNo;
	}

	public double getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(double currencyRate) {
		this.currencyRate = currencyRate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (activeEndorsement ? 1231 : 1237);
		result = prime * result + ((activedPolicyEndDate == null) ? 0 : activedPolicyEndDate.hashCode());
		result = prime * result + ((activedPolicyStartDate == null) ? 0 : activedPolicyStartDate.hashCode());
		result = prime * result + ((agent == null) ? 0 : agent.hashCode());
		result = prime * result + ((approvedBy == null) ? 0 : approvedBy.hashCode());
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((commenmanceDate == null) ? 0 : commenmanceDate.hashCode());
		result = prime * result + ((coverageDate == null) ? 0 : coverageDate.hashCode());
		long temp;
		temp = Double.doubleToLongBits(currencyRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((endorsementConfirmDate == null) ? 0 : endorsementConfirmDate.hashCode());
		result = prime * result + ((endorsementNo == null) ? 0 : endorsementNo.hashCode());
		result = prime * result + entryCount;
		result = prime * result + ((entryType == null) ? 0 : entryType.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isCoinsuranceApplied ? 1231 : 1237);
		result = prime * result + (isNonFinancialEndorse ? 1231 : 1237);
		result = prime * result + lastPaymentTerm;
		result = prime * result + ((lifeProposal == null) ? 0 : lifeProposal.hashCode());
		result = prime * result + ((organization == null) ? 0 : organization.hashCode());
		result = prime * result + ((paymentType == null) ? 0 : paymentType.hashCode());
		result = prime * result + periodMonth;
		result = prime * result + ((policyNo == null) ? 0 : policyNo.hashCode());
		result = prime * result + ((policyReferenceNo == null) ? 0 : policyReferenceNo.hashCode());
		result = prime * result + ((policyStatus == null) ? 0 : policyStatus.hashCode());
		result = prime * result + printCount;
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((renewalConfirmDate == null) ? 0 : renewalConfirmDate.hashCode());
		result = prime * result + ((saleBank == null) ? 0 : saleBank.hashCode());
		result = prime * result + ((saleChannelType == null) ? 0 : saleChannelType.hashCode());
		result = prime * result + ((salesPoints == null) ? 0 : salesPoints.hashCode());
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
		LifePolicyHistory other = (LifePolicyHistory) obj;
		if (activeEndorsement != other.activeEndorsement)
			return false;
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
		if (entryCount != other.entryCount)
			return false;
		if (entryType != other.entryType)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isCoinsuranceApplied != other.isCoinsuranceApplied)
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
		if (policyReferenceNo == null) {
			if (other.policyReferenceNo != null)
				return false;
		} else if (!policyReferenceNo.equals(other.policyReferenceNo))
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
		if (salesPoints == null) {
			if (other.salesPoints != null)
				return false;
		} else if (!salesPoints.equals(other.salesPoints))
			return false;
		if (Double.doubleToLongBits(standardExcess) != Double.doubleToLongBits(other.standardExcess))
			return false;
		if (Double.doubleToLongBits(totalDiscountAmount) != Double.doubleToLongBits(other.totalDiscountAmount))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
