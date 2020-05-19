package org.tat.fni.api.domain.lifeproposal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.tat.fni.api.common.IDataModel;
import org.tat.fni.api.common.IProposal;
import org.tat.fni.api.common.KeyFactorChecker;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;
import org.tat.fni.api.common.Utils;
import org.tat.fni.api.common.emumdata.ClassificationOfHealth;
import org.tat.fni.api.common.emumdata.EndorsementStatus;
import org.tat.fni.api.common.emumdata.ProposalStatus;
import org.tat.fni.api.common.emumdata.ProposalType;
import org.tat.fni.api.common.emumdata.SaleChannelType;
import org.tat.fni.api.domain.Agent;
import org.tat.fni.api.domain.Attachment;
import org.tat.fni.api.domain.BankBranch;
import org.tat.fni.api.domain.Branch;
import org.tat.fni.api.domain.Currency;
import org.tat.fni.api.domain.Customer;
import org.tat.fni.api.domain.LifeProposalAttachment;
import org.tat.fni.api.domain.Organization;
import org.tat.fni.api.domain.PaymentType;
import org.tat.fni.api.domain.ProposalInsuredPerson;
import org.tat.fni.api.domain.SalesPoints;
import org.tat.fni.api.domain.SurveyQuestionAnswer;

@Entity
@Table(name = TableName.LIFEPROPOSAL)
@TableGenerator(name = "LIFEPROPOSAL_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LIFEPROPOSAL_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "LifeProposal.findAll", query = "SELECT m FROM LifeProposal m "),
		@NamedQuery(name = "LifeProposal.findByDate", query = "SELECT m FROM LifeProposal m WHERE m.submittedDate BETWEEN :startDate AND :endDate"),
		@NamedQuery(name = "LifeProposal.updateCompleteStatus", query = "UPDATE LifeProposal m SET m.complete = :complete WHERE m.id = :id") })
@EntityListeners(IDInterceptor.class)
public class LifeProposal implements Serializable, IDataModel, IProposal {
	private static final long serialVersionUID = 7564214263861012292L;

	private boolean complete;
	private boolean isNonFinancialEndorse;

	@Column(name = "PERIODOFMONTH")
	private int periodMonth;
	private int paymentTerm;

	private double specialDiscount;
	private double currencyRate;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LIFEPROPOSAL_GEN")
	private String id;

	private String proposalNo;
	private String portalId;
	private boolean isSkipPaymentTLF;

	@Temporal(TemporalType.TIMESTAMP)
	private Date submittedDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	@Enumerated(EnumType.STRING)
	private ProposalType proposalType;

	@Enumerated(EnumType.STRING)
	private SaleChannelType saleChannelType;

	@Enumerated(EnumType.STRING)
	private ProposalStatus proposalStatus;

	@Enumerated(value = EnumType.STRING)
	private ClassificationOfHealth customerClsOfHealth;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCHID", referencedColumnName = "ID")
	private Branch branch;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SALESPOINTSID", referencedColumnName = "ID")
	private SalesPoints salesPoints;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMERID", referencedColumnName = "ID")
	private Customer customer;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORGANIZATIONID", referencedColumnName = "ID")
	private Organization organization;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAYMENTTYPEID", referencedColumnName = "ID")
	private PaymentType paymentType;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AGENTID", referencedColumnName = "ID")
	private Agent agent;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SALEBANKID", referencedColumnName = "ID")
	private BankBranch saleBank;

//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "LIFEPOLICYID", referencedColumnName = "ID")
//	private LifePolicy lifePolicy;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "lifeProposal", orphanRemoval = true)
	private List<ProposalInsuredPerson> proposalInsuredPersonList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "lifeProposal", orphanRemoval = true)
	private List<LifeProposalAttachment> attachmentList;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "HOLDERID", referencedColumnName = "ID")
	private List<Attachment> customerMedicalCheckUpAttachmentList;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "LIFEPROPOSALID", referencedColumnName = "ID")
	private List<SurveyQuestionAnswer> customerSurveyQuestionAnswerList;

	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public LifeProposal() {
	}

//	public LifeProposal(LifePolicy lifePolicy) {
//		this.agent = lifePolicy.getAgent();
//		this.branch = lifePolicy.getBranch();
//		this.salesPoints = lifePolicy.getSalesPoints();
//		this.customer = lifePolicy.getCustomer();
//		this.saleBank = lifePolicy.getSaleBank();
//		this.saleChannelType = lifePolicy.getSaleChannelType();
//		this.paymentType = lifePolicy.getPaymentType();
//		this.organization = lifePolicy.getOrganization();
//		this.specialDiscount = lifePolicy.getSpecialDiscount();
//		this.submittedDate = lifePolicy.getCommenmanceDate();
//		this.startDate = lifePolicy.getActivedPolicyStartDate();
//		this.endDate = lifePolicy.getActivedPolicyEndDate();
//		this.periodMonth = lifePolicy.getPeriodOfInsurance();
//		this.paymentTerm = lifePolicy.getLastPaymentTerm();
//		this.currencyRate = lifePolicy.getCurrencyRate();
//		this.lifePolicy = lifePolicy;
//
//		for (PolicyInsuredPerson iPerson : lifePolicy.getInsuredPersonInfo()) {
//			addInsuredPerson(new ProposalInsuredPerson(iPerson));
//		}
//		for (LifePolicyAttachment attachment : lifePolicy.getAttachmentList()) {
//			addAttachment(new LifeProposalAttachment(attachment));
//		}
//	}

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

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public int getPeriodMonth() {
		return periodMonth;
	}

	public void setPeriodMonth(int periodMonth) {
		this.periodMonth = periodMonth;
	}

	public int getPeriodOfInsurance() {
		if (KeyFactorChecker.isPersonalAccident(proposalInsuredPersonList.get(0).getProduct())) {
			return periodMonth;
		} else {
			return periodMonth / 12;
		}
	}

	public String getPeriod() {
		if (KeyFactorChecker.isPersonalAccident(proposalInsuredPersonList.get(0).getProduct())) {
			return periodMonth + " - Months";
		} else {
			return periodMonth / 12 + " - Year";
		}
	}

	public int getPeriodOfYears() {
		return periodMonth / 12;
	}

	public int getPremiumTerm() {
		return getPeriodOfYears() - 3;
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

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public boolean isSkipPaymentTLF() {
		return isSkipPaymentTLF;
	}

	public void setSkipPaymentTLF(boolean isSkipPaymentTLF) {
		this.isSkipPaymentTLF = isSkipPaymentTLF;
	}

	public boolean getComplete() {
		return this.complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public boolean isNonFinancialEndorse() {
		return isNonFinancialEndorse;
	}

	public void setNonFinancialEndorse(boolean isNonFinancialEndorse) {
		this.isNonFinancialEndorse = isNonFinancialEndorse;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public List<ProposalInsuredPerson> getProposalInsuredPersonList() {
		if (this.proposalInsuredPersonList == null) {
			this.proposalInsuredPersonList = new ArrayList<ProposalInsuredPerson>();
		}
		return this.proposalInsuredPersonList;
	}

	public void setInsuredPersonList(List<ProposalInsuredPerson> proposalInsuredPersonList) {
		this.proposalInsuredPersonList = proposalInsuredPersonList;
	}

	public List<LifeProposalAttachment> getAttachmentList() {
		if (this.attachmentList == null) {
			this.attachmentList = new ArrayList<LifeProposalAttachment>();
		}
		return attachmentList;
	}

	public void setAttachmentList(List<LifeProposalAttachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public void addAttachment(LifeProposalAttachment attachment) {
		if (attachmentList == null) {
			attachmentList = new ArrayList<LifeProposalAttachment>();
		}
		attachment.setLifeProposal(this);
		attachmentList.add(attachment);
	}

	public void addCustomerMedicalChkUpAttachment(Attachment attachment) {
		customerMedicalCheckUpAttachmentList.add(attachment);
	}

	public void addInsuredPerson(ProposalInsuredPerson proposalInsuredPerson) {
		if (this.proposalInsuredPersonList == null) {
			proposalInsuredPersonList = new ArrayList<ProposalInsuredPerson>();
		}
		proposalInsuredPerson.setLifeProposal(this);
		this.proposalInsuredPersonList.add(proposalInsuredPerson);
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

	public void setProposalInsuredPersonList(List<ProposalInsuredPerson> proposalInsuredPersonList) {
		this.proposalInsuredPersonList = proposalInsuredPersonList;
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

	public String getSalePersonName() {
		if (agent != null) {
			return agent.getFullName();
		} else if (saleBank != null) {
			return saleBank.getName();
		}
		return null;
	}

	public ProposalStatus getProposalStatus() {
		return proposalStatus;
	}

	public void setProposalStatus(ProposalStatus proposalStatus) {
		this.proposalStatus = proposalStatus;
	}

	/* System Auto Calculate Process */
	public double getPremium() {
		double premium = 0.0;
		for (ProposalInsuredPerson pv : proposalInsuredPersonList) {
			if (pv.getEndorsementStatus() != EndorsementStatus.TERMINATE) {
				premium = Utils.getTwoDecimalPoint(premium + (pv.getProposedPremium() + pv.getAddOnPremium()));
			}
		}
		return premium;
	}

	public double getApprovedPremium() {
		double premium = 0.0;
		for (ProposalInsuredPerson pv : proposalInsuredPersonList) {
			if (pv.getEndorsementStatus() != EndorsementStatus.TERMINATE) {
				if (pv.isApproved()) {
					premium = Utils.getTwoDecimalPoint(premium + pv.getProposedPremium());
				}
			}
		}
		return premium;
	}

	public double getApprovedAddOnPremium() {
		double premium = 0.0;
		for (ProposalInsuredPerson pi : proposalInsuredPersonList) {
			if (pi.getEndorsementStatus() != EndorsementStatus.TERMINATE) {
				if (pi.isApproved()) {
					premium = Utils.getTwoDecimalPoint(premium + pi.getAddOnPremium());
				}
			}
		}
		return premium;
	}

	public double getAddOnPremium() {
		double premium = 0.0;
		for (ProposalInsuredPerson pi : proposalInsuredPersonList) {
			if (pi.getEndorsementStatus() != EndorsementStatus.TERMINATE) {
				premium = Utils.getTwoDecimalPoint(premium + pi.getAddOnPremium());
			}
		}
		return premium;
	}

	public double getProposedAddOnPremium() {
		double premium = 0.0;
		for (ProposalInsuredPerson pi : proposalInsuredPersonList) {
			if (pi.getEndorsementStatus() != EndorsementStatus.TERMINATE) {
				if (pi.isApproved()) {
					premium = Utils.getTwoDecimalPoint(premium + pi.getAddOnPremium());
				}
			}
		}
		return premium;
	}

	public double getTotalBasicTermPremium() {
		double termPermium = 0.0;
		for (ProposalInsuredPerson pv : proposalInsuredPersonList) {
			if (pv.getEndorsementStatus() != EndorsementStatus.TERMINATE) {
				termPermium = Utils.getTwoDecimalPoint(termPermium + pv.getBasicTermPremium());
			}
		}
		return termPermium;
	}

	public double getTotalAddOnTermPremium() {
		double termPermium = 0.0;
		for (ProposalInsuredPerson pv : proposalInsuredPersonList) {
			if (pv.getEndorsementStatus() != EndorsementStatus.TERMINATE) {
				termPermium = Utils.getTwoDecimalPoint(termPermium + pv.getAddOnTermPremium());
			}
		}
		return termPermium;
	}

	public double getStampFees() {
		double stampFees = 0.0;
		for (ProposalInsuredPerson pi : proposalInsuredPersonList) {
			stampFees = stampFees + pi.getProposedSumInsured() * 0.003;
		}
		return stampFees;
	}

	public double getSumInsured() {
		double sumInsured = 0.0;
		for (ProposalInsuredPerson pi : proposalInsuredPersonList) {
			if (pi.getEndorsementStatus() != EndorsementStatus.TERMINATE) {
				sumInsured = sumInsured + pi.getProposedSumInsured();
			}
		}
		return sumInsured;
	}

	public double getTotalCalculateSumInsured() {
		double sumInsured = 0.0;
		for (ProposalInsuredPerson pi : proposalInsuredPersonList) {
			if (pi.getEndorsementStatus() != EndorsementStatus.TERMINATE) {
				sumInsured = sumInsured + pi.getCalculateSumInsured();
			}
		}
		return sumInsured;
	}

	public int getUnits() {
		int unit = 0;
		for (ProposalInsuredPerson pi : proposalInsuredPersonList) {
			if (pi.getUnit() > 0) {
				unit = unit + pi.getUnit();
			}
		}
		return unit;
	}

	public double getTotalUntis() {
		double unit = 0.0;
		for (ProposalInsuredPerson pi : proposalInsuredPersonList) {
			if (pi.getUnit() > 0) {
				unit = unit + pi.getUnit();
			}
		}
		return unit;
	}

	public double getSuminsuredPerUnit() {
		double siPerUnit = 0.0;
		for (ProposalInsuredPerson pi : proposalInsuredPersonList) {
			if (pi.getUnit() > 0) {
				siPerUnit = siPerUnit + pi.getSuminsuredPerUnit();
			}
		}
		return siPerUnit;
	}

	public double getProposedPremium() {
		double proposedPremium = 0.0;
		for (ProposalInsuredPerson pi : proposalInsuredPersonList) {
			if (pi.getProposedPremium() > 0) {
				proposedPremium = proposedPremium + pi.getProposedPremium();
			}
		}
		return proposedPremium;
	}

	public double getApprovedSumInsured() {
		double sumInsured = 0.0;
		for (ProposalInsuredPerson pi : proposalInsuredPersonList) {
			if (pi.getEndorsementStatus() != EndorsementStatus.TERMINATE) {
				if (pi.isApproved()) {
					sumInsured = sumInsured + pi.getProposedSumInsured();
				}
			}
		}
		return sumInsured;
	}

	public double getTotalAmount() {
		return Utils.getTwoDecimalPoint(getTotalBasicTermPremium() + getTotalAddOnTermPremium() + getStampFees());
	}

	public double getAddOnSumInsured() {
		double sumInsured = 0.0;
		for (ProposalInsuredPerson pi : proposalInsuredPersonList) {
			if (pi.getEndorsementStatus() != EndorsementStatus.TERMINATE) {
				sumInsured = sumInsured + pi.getAddOnSumInsured();
			}
		}
		return sumInsured;
	}

	public double getApprovedAddOnSumInsured() {
		double sumInsured = 0.0;
		for (ProposalInsuredPerson pi : proposalInsuredPersonList) {
			if (pi.getEndorsementStatus() != EndorsementStatus.TERMINATE) {
				if (pi.isApproved()) {
					sumInsured = sumInsured + pi.getAddOnSumInsured();
				}
			}
		}
		return sumInsured;
	}

	public double getTotalPremium() {
		return Utils.getTwoDecimalPoint(getPremium() + getAddOnPremium());
	}

	public double getTotalSumInsured() {
		return getSumInsured() + getAddOnSumInsured();
	}

	public double getApprovedTotalSumInsured() {
		return getApprovedSumInsured() + getApprovedAddOnSumInsured();
	}

	public double getTotalTermPremium() {
		return Utils.getTwoDecimalPoint(getTotalBasicTermPremium() + getTotalAddOnTermPremium());
	}

	public double getPATotoalPremium() {
		double result = 0.0;
		if (!proposalInsuredPersonList.isEmpty()) {
			result = proposalInsuredPersonList.get(0).getProposedPremium() + getPAAddonPremium();
		}
		return result;
	}

	public double getPAAddonPremium() {
		double result = 0.0;
		for (ProposalInsuredPerson pi : proposalInsuredPersonList) {
			if (!pi.getInsuredPersonAddOnList().isEmpty()) {
				result = pi.getInsuredPersonAddOnList().get(0).getProposedPremium();
			}
		}
		return result;
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

	public String getPhoneNo() {
		if (customer != null) {
			return customer.getContentInfo().getPhone();
		}
		if (organization != null) {
			return organization.getContentInfo().getPhone();
		}
		return null;
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

	public String getCustomerAddress() {
		if (customer != null) {
			return customer.getFullAddress();
		}
		if (organization != null) {
			return organization.getFullAddress();
		}
		return null;
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

	public double getEndorsementNetPremium() {
		double amount = 0.0;
		for (ProposalInsuredPerson pi : proposalInsuredPersonList) {

			amount = Utils.getTwoDecimalPoint(amount + pi.getEndorsementNetBasicPremium());

		}
		return amount;
	}

	public double getEndorsementAddOnPremium() {
		double amount = 0.0;
		for (ProposalInsuredPerson pi : proposalInsuredPersonList) {
			if (pi.isApproved()) {
				amount = Utils.getTwoDecimalPoint(amount + pi.getEndorsementNetAddonPremium());
			}
		}
		return amount;
	}

	public double getTotalEndorsementNetPremium() {
		return Utils.getTwoDecimalPoint(getEndorsementNetPremium() + getEndorsementAddOnPremium());
	}

	public double getTotalInterest() {
		double interest = 0.0;
		for (ProposalInsuredPerson pi : proposalInsuredPersonList) {
			if (pi.isApproved()) {
				interest = interest + pi.getInterest();
			}
		}
		return interest;
	}

//	public LifePolicy getLifePolicy() {
//		return lifePolicy;
//	}
//
//	public void setLifePolicy(LifePolicy lifePolicy) {
//		this.lifePolicy = lifePolicy;
//	}

	public ProposalType getProposalType() {
		return proposalType;
	}

	public void setProposalType(ProposalType proposalType) {
		this.proposalType = proposalType;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public int getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(int paymentTerm) {
		this.paymentTerm = paymentTerm;
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

	public double getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(double currencyRate) {
		this.currencyRate = currencyRate;
	}

	public ClassificationOfHealth getCustomerClsOfHealth() {
		return customerClsOfHealth;
	}

	public void setCustomerClsOfHealth(ClassificationOfHealth customerClsOfHealth) {
		this.customerClsOfHealth = customerClsOfHealth;
	}

	public List<Attachment> getCustomerMedicalCheckUpAttachmentList() {
		return customerMedicalCheckUpAttachmentList;
	}

	public void setCustomerMedicalCheckUpAttachmentList(List<Attachment> customerMedicalCheckUpAttachmentList) {
		this.customerMedicalCheckUpAttachmentList = customerMedicalCheckUpAttachmentList;
	}

	public List<SurveyQuestionAnswer> getCustomerSurveyQuestionAnswerList() {
		return customerSurveyQuestionAnswerList;
	}

	public void setCustomerSurveyQuestionAnswerList(List<SurveyQuestionAnswer> customerSurveyQuestionAnswerList) {
		this.customerSurveyQuestionAnswerList = customerSurveyQuestionAnswerList;
	}

	public void addCustomerSurveyQuestionAnswerList(SurveyQuestionAnswer surveyQuestion) {
		getCustomerSurveyQuestionAnswerList().add(surveyQuestion);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agent == null) ? 0 : agent.hashCode());
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + (complete ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(currencyRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isNonFinancialEndorse ? 1231 : 1237);
//		result = prime * result + ((lifePolicy == null) ? 0 : lifePolicy.hashCode());
		result = prime * result + ((organization == null) ? 0 : organization.hashCode());
		result = prime * result + paymentTerm;
		result = prime * result + ((paymentType == null) ? 0 : paymentType.hashCode());
		result = prime * result + periodMonth;
		result = prime * result + ((portalId == null) ? 0 : portalId.hashCode());
		result = prime * result + ((proposalNo == null) ? 0 : proposalNo.hashCode());
		result = prime * result + ((proposalStatus == null) ? 0 : proposalStatus.hashCode());
		result = prime * result + ((proposalType == null) ? 0 : proposalType.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((saleBank == null) ? 0 : saleBank.hashCode());
		result = prime * result + ((saleChannelType == null) ? 0 : saleChannelType.hashCode());
		result = prime * result + ((salesPoints == null) ? 0 : salesPoints.hashCode());
		temp = Double.doubleToLongBits(specialDiscount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((submittedDate == null) ? 0 : submittedDate.hashCode());
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
		LifeProposal other = (LifeProposal) obj;
		if (agent == null) {
			if (other.agent != null)
				return false;
		} else if (!agent.equals(other.agent))
			return false;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (complete != other.complete)
			return false;
		if (Double.doubleToLongBits(currencyRate) != Double.doubleToLongBits(other.currencyRate))
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isNonFinancialEndorse != other.isNonFinancialEndorse)
			return false;
//		if (lifePolicy == null) {
//			if (other.lifePolicy != null)
//				return false;
//		} else if (!lifePolicy.equals(other.lifePolicy))
//			return false;
		if (organization == null) {
			if (other.organization != null)
				return false;
		} else if (!organization.equals(other.organization))
			return false;
		if (paymentTerm != other.paymentTerm)
			return false;
		if (paymentType == null) {
			if (other.paymentType != null)
				return false;
		} else if (!paymentType.equals(other.paymentType))
			return false;
		if (periodMonth != other.periodMonth)
			return false;
		if (portalId == null) {
			if (other.portalId != null)
				return false;
		} else if (!portalId.equals(other.portalId))
			return false;
		if (proposalNo == null) {
			if (other.proposalNo != null)
				return false;
		} else if (!proposalNo.equals(other.proposalNo))
			return false;
		if (proposalStatus != other.proposalStatus)
			return false;
		if (proposalType != other.proposalType)
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
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
		if (Double.doubleToLongBits(specialDiscount) != Double.doubleToLongBits(other.specialDiscount))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (submittedDate == null) {
			if (other.submittedDate != null)
				return false;
		} else if (!submittedDate.equals(other.submittedDate))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	@Override
	public String getUserType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Currency getCurrency() {
		// TODO Auto-generated method stub
		return null;
	}

	public double getTotalDiscountAmount() {
		double specialDiscountAmount = Utils.getPercentOf(specialDiscount, getProposedPremium());
		return Utils.getTwoDecimalPoint(specialDiscountAmount);
	}

	public double getNetPremium() {
		return getTotalPremium() - getTotalDiscountAmount();
	}

	public double getTotalTermDiscountAmount() {
		double specialDiscountAmount = Utils.getPercentOf(specialDiscount, getTotalTermPremium());
		return Utils.getTwoDecimalPoint(specialDiscountAmount);
	}

	public double getNetTermPremium() {
		return getTotalTermPremium() - getTotalTermDiscountAmount();
	}

	public String getSalePointName() {
		if (salesPoints != null)
			return salesPoints.getName();
		else
			return "";
	}

	public String getPremiumRateStr() {
		String premiumRateStr = "";
		Map<String, String> rateMap = new HashMap<String, String>();
		for (ProposalInsuredPerson p : getProposalInsuredPersonList()) {
			rateMap.put(p.getPremiumRate() + "", p.getPremiumRate() + "");
		}
		int count = 1;
		int size = rateMap.size();
		for (String rateStr : rateMap.values()) {
			premiumRateStr += rateStr;
			if (count++ != size)
				premiumRateStr += ", ";
		}
		return premiumRateStr;
	}

}
