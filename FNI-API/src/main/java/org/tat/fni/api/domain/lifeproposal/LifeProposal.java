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
import org.tat.fni.api.common.emumdata.EndorsementStatus;
import org.tat.fni.api.common.emumdata.ProposalStatus;
import org.tat.fni.api.common.emumdata.ProposalType;
import org.tat.fni.api.common.emumdata.SaleChannelType;
import org.tat.fni.api.common.utils.Utils;
import org.tat.fni.api.domain.Agent;
import org.tat.fni.api.domain.Attachment;
import org.tat.fni.api.domain.Currency;
import org.tat.fni.api.domain.Customer;
import org.tat.fni.api.domain.LifeProposalAttachment;
import org.tat.fni.api.domain.Organization;
import org.tat.fni.api.domain.PaymentType;
import org.tat.fni.api.domain.ProposalInsuredPerson;
import org.tat.fni.api.domain.SalesPoints;
import org.tat.fni.api.domain.SurveyQuestionAnswer;

import lombok.Data;

@Entity
@Table(name = TableName.LIFEPROPOSAL)
@TableGenerator(name = "LIFEPROPOSAL_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LIFEPROPOSAL_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "LifeProposal.findAll", query = "SELECT m FROM LifeProposal m "),
		@NamedQuery(name = "LifeProposal.findByDate", query = "SELECT m FROM LifeProposal m WHERE m.submittedDate BETWEEN :startDate AND :endDate"),
		@NamedQuery(name = "LifeProposal.updateCompleteStatus", query = "UPDATE LifeProposal m SET m.complete = :complete WHERE m.id = :id") })
@EntityListeners(IDInterceptor.class)
@Data
public class LifeProposal implements Serializable, IDataModel, IProposal {
	private static final long serialVersionUID = 7564214263861012292L;

	private boolean complete;

	@Column(name = "PERIODOFMONTH")
	private int periodMonth;
	
	private int paymentTerm;

	
	private double currencyRate;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LIFEPROPOSAL_GEN")
	private String id;

	private String proposalNo;

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

	public List<ProposalInsuredPerson> getProposalInsuredPersonList() {
		if (this.proposalInsuredPersonList == null) {
			this.proposalInsuredPersonList = new ArrayList<ProposalInsuredPerson>();
		}
		return this.proposalInsuredPersonList;
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
		}  else if (saleChannelType.equals(SaleChannelType.WALKIN)) {
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

	public double getNetPremium() {
		return getTotalPremium();
	}

	public double getNetTermPremium() {
		return getTotalTermPremium();
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

	@Override
	public Currency getCurrency() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserType() {
		// TODO Auto-generated method stub
		return null;
	}

}
