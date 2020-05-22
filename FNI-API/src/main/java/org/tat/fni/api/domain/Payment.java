package org.tat.fni.api.domain;

import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.IDataModel;
import org.tat.fni.api.common.PaymentDTO;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;
import org.tat.fni.api.common.emumdata.PaymentChannel;

@Entity
@Table(name = TableName.PAYMENT)
@TableGenerator(name = "PAYMENT_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "PAYMENT_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "Payment.findAll", query = "SELECT m FROM Payment m "),
		@NamedQuery(name = "Payment.findById", query = "SELECT m FROM Payment m WHERE m.id = :id"),
		@NamedQuery(name = "Payment.findByReferenceNo", query = "SELECT m FROM Payment m WHERE m.referenceNo = :referenceNo ORDER BY m.paymentDate"),
//		@NamedQuery(name = "Payment.findByReferenceNoAndReferenceType", query = "SELECT m FROM Payment m WHERE m.referenceNo = :referenceNo and m.complete = :complete and m.referenceType = :referenceType"),
//		@NamedQuery(name = "Payment.findByReferenceNoAndReferenceTypeComplete", query = "SELECT m FROM Payment m WHERE m.complete = TRUE and m.referenceNo = :referenceNo and m.referenceType = :referenceType"),
		@NamedQuery(name = "Payment.findPaymentByReferenceNoAndMaxDate", query = "select p from Payment p where p.paymentDate=(select MIN(p1.paymentDate) from Payment p1 where  p1.referenceNo = :referenceNo) ") })
@EntityListeners(IDInterceptor.class)
public class Payment implements Serializable, IDataModel {
	private static final long serialVersionUID = 6880993048581143599L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "PAYMENT_GEN")
	private String id;

	@Column(name = "RECEIPTNO")
	private String receiptNo;

	@Column(name = "BANKACCOUNTNO")
	private String bankAccountNo;

	@Column(name = "REFERENCENO")
	private String referenceNo;

	@Column(name = "CHEQUENO")
	private String chequeNo;

//	@Column(name = "REFERENCETYPE")
//	@Enumerated(value = EnumType.STRING)
//	private PolicyReferenceType referenceType;

	@Column(name = "RECEIVED_DENO")
	private String receivedDeno;

	@Column(name = "REFUND_DENO")
	private String refundDeno;

	@Column(name = "RATE")
	private double rate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUR", referencedColumnName = "ID")
	private Currency currency;

	private String invoiceNo;
	private String policyNo;

	private double basicPremium;
	private double addOnPremium;
	private double amount;
	private double claimAmount;
	private double administrationFees;
	private double ncbPremium;
	private double penaltyPremium;
	private double servicesCharges;
	private double stampFees;
	private double medicalFees;
	private double reinstatementPremium;
	private double specialDiscount;
	private double afpDiscount;
	private double fleetDiscount;

	private double agentCommission;

	private boolean complete;
	private boolean isOutstanding;

	@Enumerated(value = EnumType.STRING)
	private PaymentChannel paymentChannel;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANKID", referencedColumnName = "ID")
	private Bank bank;

	@Column(name = "CONFIRMDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date confirmDate;

	@Column(name = "PAYMENTDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date paymentDate;

	@Column(name = "ENDORSEMENTCONFIRMDATE")
	@Temporal(TemporalType.DATE)
	private Date endorsementConfirmDate;

	@Column(name = "ENDORSEMENTPAYMENTDATE")
	@Temporal(TemporalType.DATE)
	private Date endorsementPaymentDate;

//	@Column(name = "POLICYSTATUS")
//	@Enumerated(value = EnumType.STRING)
//	private PolicyStatus policyStatus;

	@Column(name = "FROMTERM")
	private int fromTerm;

	@Column(name = "TOTERM")
	private int toTerm;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "PAYMENTTYPEID", referencedColumnName = "ID")
	private PaymentType paymentType;

	@Column(name = "LOANINTEREST")
	private double loanInterest;

	@Column(name = "RENEWALINTEREST")
	private double renewalInterest;

	@Column(name = "REFUND")
	private double refund;

	@Column(name = "PONO")
	private String poNo;

	@Column(name = "ISPO")
	private boolean isPO;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACCOUNTBANKID", referencedColumnName = "ID")
	private Bank accountBank;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SALESPOINTSID", referencedColumnName = "ID")
	private SalesPoints salesPoints;

	private boolean isReverse;
	private boolean isReinstate;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public Payment() {
		this.rate = 1;
	}

//	public Payment(String receiptNo, String bankAccountNo, String referenceNo, PolicyReferenceType referenceType, double basicPremium, double addOnPremium, double discountPercent,
//			double servicesCharges, double stampFees, boolean complete, String receivedDeno, String refundDeno, PaymentChannel paymentChannel, Bank bank, double medicalFees) {
//		this.receiptNo = receiptNo;
//		this.bankAccountNo = bankAccountNo;
//		this.referenceNo = referenceNo;
//		this.referenceType = referenceType;
//		this.basicPremium = basicPremium;
//		this.addOnPremium = addOnPremium;
//		this.specialDiscount = discountPercent;
//		this.servicesCharges = servicesCharges;
//		this.stampFees = stampFees;
//		this.complete = complete;
//		this.receivedDeno = receivedDeno;
//		this.refundDeno = refundDeno;
//		this.paymentChannel = paymentChannel;
//		this.bank = bank;
//		this.medicalFees = medicalFees;
//	}
	
	public Payment(String receiptNo, String bankAccountNo, String referenceNo, double basicPremium, double addOnPremium, double discountPercent,
			double servicesCharges, double stampFees, boolean complete, String receivedDeno, String refundDeno, PaymentChannel paymentChannel, Bank bank, double medicalFees) {
		this.receiptNo = receiptNo;
		this.bankAccountNo = bankAccountNo;
		this.referenceNo = referenceNo;
		this.basicPremium = basicPremium;
		this.addOnPremium = addOnPremium;
		this.specialDiscount = discountPercent;
		this.servicesCharges = servicesCharges;
		this.stampFees = stampFees;
		this.complete = complete;
		this.receivedDeno = receivedDeno;
		this.refundDeno = refundDeno;
		this.paymentChannel = paymentChannel;
		this.bank = bank;
		this.medicalFees = medicalFees;
	}

	public Payment(PaymentDTO tempPayment) {
		this.invoiceNo = tempPayment.getInvoiceNo();
		this.receiptNo = tempPayment.getReceiptNo();
		this.bankAccountNo = tempPayment.getBankAccountNo();
		this.referenceNo = tempPayment.getReferenceNo();
//		this.referenceType = tempPayment.getReferenceType();
		this.specialDiscount = tempPayment.getDiscountPercent();
		this.servicesCharges = tempPayment.getServicesCharges();
		this.stampFees = tempPayment.getStampFees();
		this.receivedDeno = tempPayment.getReceivedDeno();
		this.refundDeno = tempPayment.getRefundDeno();
		this.claimAmount = tempPayment.getClaimAmount();
		this.complete = tempPayment.isComplete();
		this.paymentChannel = tempPayment.getPaymentChannel();
		this.chequeNo = tempPayment.getChequeNo();
		this.bank = tempPayment.getBank();
		this.reinstatementPremium = tempPayment.getReinstatementPremium();
		this.administrationFees = tempPayment.getAdministrationFees();
		this.ncbPremium = tempPayment.getNcbPremium();
		this.penaltyPremium = tempPayment.getPenaltyPremium();
		this.confirmDate = tempPayment.getConfirmDate();
		this.paymentDate = tempPayment.getPaymentDate();
		this.basicPremium = tempPayment.getBasicPremium();
		this.addOnPremium = tempPayment.getAddOnPremium();
		this.fromTerm = tempPayment.getFromTerm();
		this.toTerm = tempPayment.getToTerm();
		this.paymentType = tempPayment.getPaymentType();
		this.loanInterest = tempPayment.getLoanInterest();
		this.renewalInterest = tempPayment.getRenewalInterest();
		this.refund = tempPayment.getRefund();
		this.poNo = tempPayment.getPoNo();
		this.accountBank = tempPayment.getAccountBank();
		this.isOutstanding = tempPayment.isOutstanding();
		this.medicalFees = tempPayment.getMedicalFees();
	}

	public Payment(List<Payment> paymentList) {
		if (paymentList != null && !paymentList.isEmpty()) {
			Payment tempPayment = paymentList.get(0);
			this.receiptNo = tempPayment.getReceiptNo();
			this.invoiceNo = tempPayment.getInvoiceNo();
			this.bankAccountNo = tempPayment.getBankAccountNo();
			this.referenceNo = tempPayment.getReferenceNo();
//			this.referenceType = tempPayment.getReferenceType();
			this.specialDiscount = tempPayment.getSpecialDiscount();
			this.servicesCharges = tempPayment.getServicesCharges();
			this.stampFees = tempPayment.getStampFees();
			this.receivedDeno = tempPayment.getReceivedDeno();
			this.refundDeno = tempPayment.getRefundDeno();
			this.claimAmount = tempPayment.getClaimAmount();
			this.complete = tempPayment.isComplete();
			this.paymentChannel = tempPayment.getPaymentChannel();
			this.chequeNo = tempPayment.getChequeNo();
			this.bank = tempPayment.getBank();
			this.reinstatementPremium = tempPayment.getReinstatementPremium();
			this.administrationFees = tempPayment.getAdministrationFees();
			this.ncbPremium = tempPayment.getNcbPremium();
			this.penaltyPremium = tempPayment.getPenaltyPremium();
			double basicPremium = 0;
			double addOnPremium = 0;
			this.confirmDate = tempPayment.getConfirmDate();
			this.paymentDate = tempPayment.getPaymentDate();
			for (Payment p : paymentList) {
				basicPremium = basicPremium + p.getBasicPremium();
				addOnPremium = addOnPremium + p.getAddOnPremium();
			}
			this.basicPremium = basicPremium;
			this.addOnPremium = addOnPremium;
			this.fromTerm = tempPayment.getFromTerm();
			this.toTerm = tempPayment.getToTerm();
			this.paymentType = tempPayment.getPaymentType();
			this.loanInterest = tempPayment.getLoanInterest();
			this.renewalInterest = tempPayment.getRenewalInterest();
			this.refund = tempPayment.getRefund();
			this.poNo = tempPayment.getPoNo();
			this.accountBank = tempPayment.getAccountBank();
			this.isOutstanding = tempPayment.isOutstanding();
			this.medicalFees = tempPayment.getMedicalFees();
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isOutstanding() {
		return isOutstanding;
	}

	public void setOutstanding(boolean isOutstanding) {
		this.isOutstanding = isOutstanding;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

//	public PolicyReferenceType getReferenceType() {
//		return referenceType;
//	}
//
//	public void setReferenceType(PolicyReferenceType referenceType) {
//		this.referenceType = referenceType;
//	}

	public double getBasicPremium() {
		return basicPremium;
	}

	public void setBasicPremium(double basicPremium) {
		this.basicPremium = basicPremium;
	}

	public double getAddOnPremium() {
		return addOnPremium;
	}

	public void setAddOnPremium(double addOnPremium) {
		this.addOnPremium = addOnPremium;
	}

	public double getSpecialDiscount() {
		return specialDiscount;
	}

	public void setSpecialDiscount(double specialDiscount) {
		this.specialDiscount = specialDiscount;
	}

	public double getTotalDiscount() {
		double discountAmount = specialDiscount + afpDiscount + fleetDiscount;
		return discountAmount;
	}

	public double getServicesCharges() {
		return servicesCharges;
	}

	public void setServicesCharges(double servicesCharges) {
		this.servicesCharges = servicesCharges;
	}

	public double getStampFees() {
		return stampFees;
	}

	public void setStampFees(double stampFees) {
		this.stampFees = stampFees;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public String getReceivedDeno() {
		return receivedDeno;
	}

	public void setReceivedDeno(String receivedDeno) {
		this.receivedDeno = receivedDeno;
	}

	public String getRefundDeno() {
		return refundDeno;
	}

	public void setRefundDeno(String refundDeno) {
		this.refundDeno = refundDeno;
	}

	public PaymentChannel getPaymentChannel() {
		return paymentChannel;
	}

	public void setPaymentChannel(PaymentChannel paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public double getAdministrationFees() {
		return administrationFees;
	}

	public void setAdministrationFees(double administrationFees) {
		this.administrationFees = administrationFees;
	}

	public double getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(double claimAmount) {
		this.claimAmount = claimAmount;
	}

	public double getNetClaimAmount() {
		return claimAmount - servicesCharges;
	}

//	public PolicyStatus getPolicyStatus() {
//		return policyStatus;
//	}
//
//	public void setPolicyStatus(PolicyStatus policyStatus) {
//		this.policyStatus = policyStatus;
//	}

	public double getReinstatementPremium() {
		return reinstatementPremium;
	}

	public void setReinstatementPremium(double reinstatementPremium) {
		this.reinstatementPremium = reinstatementPremium;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Date getEndorsementConfirmDate() {
		return endorsementConfirmDate;
	}

	public void setEndorsementConfirmDate(Date endorsementConfirmDate) {
		this.endorsementConfirmDate = endorsementConfirmDate;
	}

	public Date getEndorsementPaymentDate() {
		return endorsementPaymentDate;
	}

	public void setEndorsementPaymentDate(Date endorsementPaymentDate) {
		this.endorsementPaymentDate = endorsementPaymentDate;
	}

	public int getFromTerm() {
		return fromTerm;
	}

	public void setFromTerm(int fromTerm) {
		this.fromTerm = fromTerm;
	}

	public int getToTerm() {
		return toTerm;
	}

	public void setToTerm(int toTerm) {
		this.toTerm = toTerm;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public double getLoanInterest() {
		return loanInterest;
	}

	public void setLoanInterest(double loanInterest) {
		this.loanInterest = loanInterest;
	}

	public double getRenewalInterest() {
		return renewalInterest;
	}

	public void setRenewalInterest(double renewalInterest) {
		this.renewalInterest = renewalInterest;
	}

	public double getRefund() {
		return refund;
	}

	public void setRefund(double refund) {
		this.refund = refund;
	}

	public double getNcbPremium() {
		return ncbPremium;
	}

	public void setNcbPremium(double ncbPremium) {
		this.ncbPremium = ncbPremium;
	}

	public double getPenaltyPremium() {
		return penaltyPremium;
	}

	public void setPenaltyPremium(double penaltyPremium) {
		this.penaltyPremium = penaltyPremium;
	}

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public boolean isPO() {
		return isPO;
	}

	public void setPO(boolean isPO) {
		this.isPO = isPO;
	}

	public Bank getAccountBank() {
		return accountBank;
	}

	public void setAccountBank(Bank accountBank) {
		this.accountBank = accountBank;
	}

	public boolean isReverse() {
		return isReverse;
	}

	public void setReverse(boolean isReverse) {
		this.isReverse = isReverse;
	}

	public boolean getIsReinstate() {
		return isReinstate;
	}

	public void setIsReinstate(boolean isReinstate) {
		this.isReinstate = isReinstate;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public SalesPoints getSalesPoints() {
		return salesPoints;
	}

	public void setSalesPoints(SalesPoints salesPoints) {
		this.salesPoints = salesPoints;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public double getAfpDiscount() {
		return this.afpDiscount;
	}

	public void setAfpDiscount(double afpDiscount) {
		this.afpDiscount = afpDiscount;
	}

	public double getFleetDiscount() {
		return fleetDiscount;
	}

	public void setFleetDiscount(double fleetDiscount) {
		this.fleetDiscount = fleetDiscount;
	}

	public double getAgentCommission() {
		return agentCommission;
	}

	public void setAgentCommission(double agentCommission) {
		this.agentCommission = agentCommission;
	}

	public double getMedicalFees() {
		return medicalFees;
	}

	public void setMedicalFees(double medicalFees) {
		this.medicalFees = medicalFees;
	}

	/***************************
	 * System Generated Method
	 ***************************/
	public double getTotalClaimAmount() {
		return getClaimAmount() - (servicesCharges + reinstatementPremium);
	}

	public double getServiceChargeAndAdminFee() {
		return servicesCharges + administrationFees;
	}

	public double getTotalPremium() {
		return basicPremium + addOnPremium + penaltyPremium;
	}

	public double getRenewalTotalPremium() {
		return (basicPremium - ncbPremium) + addOnPremium + penaltyPremium;
	}

	public double getRenewalNetPremium() {
		return getRenewalTotalPremium() - getTotalDiscount();
	}

	public double getNetPremium() {
		return getTotalPremium() - getTotalDiscount() - getNcbPremium() + getReinstatementPremium();
	}

	public double getNetAgentComPremium() {
		return getNetPremium() + servicesCharges + administrationFees;
	}

	public double getTotalAmount() {
		return getNetPremium() + servicesCharges + administrationFees + stampFees;
	}

	public double getTermNetAgentComPremium() {
		return getNetAgentComPremium() / getPaymentTimes();
	}

	public int getPaymentTimes() {
		return (toTerm - fromTerm) + 1;
	}

	public String getPaymentTermStrings() {
		StringBuffer buffer = new StringBuffer();
		for (int i = fromTerm; i <= toTerm; i++) {
			buffer.append(i);
			if (i < toTerm) {
				buffer.append(", ");
			}
		}
		return buffer.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(afpDiscount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((accountBank == null) ? 0 : accountBank.hashCode());
		temp = Double.doubleToLongBits(addOnPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(administrationFees);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((bank == null) ? 0 : bank.hashCode());
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((bankAccountNo == null) ? 0 : bankAccountNo.hashCode());
		temp = Double.doubleToLongBits(basicPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((chequeNo == null) ? 0 : chequeNo.hashCode());
		temp = Double.doubleToLongBits(claimAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (complete ? 1231 : 1237);
		result = prime * result + ((confirmDate == null) ? 0 : confirmDate.hashCode());
		temp = Double.doubleToLongBits(specialDiscount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((endorsementConfirmDate == null) ? 0 : endorsementConfirmDate.hashCode());
		result = prime * result + ((endorsementPaymentDate == null) ? 0 : endorsementPaymentDate.hashCode());
		temp = Double.doubleToLongBits(fleetDiscount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + fromTerm;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isOutstanding ? 1231 : 1237);
		result = prime * result + (isPO ? 1231 : 1237);
		result = prime * result + (isReinstate ? 1231 : 1237);
		result = prime * result + (isReverse ? 1231 : 1237);
		temp = Double.doubleToLongBits(loanInterest);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(ncbPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((paymentChannel == null) ? 0 : paymentChannel.hashCode());
		result = prime * result + ((paymentDate == null) ? 0 : paymentDate.hashCode());
		result = prime * result + ((paymentType == null) ? 0 : paymentType.hashCode());
		temp = Double.doubleToLongBits(penaltyPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((poNo == null) ? 0 : poNo.hashCode());
		result = prime * result + ((policyNo == null) ? 0 : policyNo.hashCode());
//		result = prime * result + ((policyStatus == null) ? 0 : policyStatus.hashCode());
		temp = Double.doubleToLongBits(rate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((receiptNo == null) ? 0 : receiptNo.hashCode());
		result = prime * result + ((receivedDeno == null) ? 0 : receivedDeno.hashCode());
		result = prime * result + ((referenceNo == null) ? 0 : referenceNo.hashCode());
//		result = prime * result + ((referenceType == null) ? 0 : referenceType.hashCode());
		temp = Double.doubleToLongBits(refund);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((refundDeno == null) ? 0 : refundDeno.hashCode());
		temp = Double.doubleToLongBits(reinstatementPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(renewalInterest);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((salesPoints == null) ? 0 : salesPoints.hashCode());
		temp = Double.doubleToLongBits(servicesCharges);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(stampFees);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + toTerm;
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
		Payment other = (Payment) obj;
		if (Double.doubleToLongBits(afpDiscount) != Double.doubleToLongBits(other.afpDiscount))
			return false;
		if (accountBank == null) {
			if (other.accountBank != null)
				return false;
		} else if (!accountBank.equals(other.accountBank))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (Double.doubleToLongBits(addOnPremium) != Double.doubleToLongBits(other.addOnPremium))
			return false;
		if (Double.doubleToLongBits(administrationFees) != Double.doubleToLongBits(other.administrationFees))
			return false;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (bank == null) {
			if (other.bank != null)
				return false;
		} else if (!bank.equals(other.bank))
			return false;
		if (bankAccountNo == null) {
			if (other.bankAccountNo != null)
				return false;
		} else if (!bankAccountNo.equals(other.bankAccountNo))
			return false;
		if (Double.doubleToLongBits(basicPremium) != Double.doubleToLongBits(other.basicPremium))
			return false;
		if (chequeNo == null) {
			if (other.chequeNo != null)
				return false;
		} else if (!chequeNo.equals(other.chequeNo))
			return false;
		if (Double.doubleToLongBits(claimAmount) != Double.doubleToLongBits(other.claimAmount))
			return false;
		if (complete != other.complete)
			return false;
		if (confirmDate == null) {
			if (other.confirmDate != null)
				return false;
		} else if (!confirmDate.equals(other.confirmDate))
			return false;
		if (Double.doubleToLongBits(specialDiscount) != Double.doubleToLongBits(other.specialDiscount))
			return false;
		if (endorsementConfirmDate == null) {
			if (other.endorsementConfirmDate != null)
				return false;
		} else if (!endorsementConfirmDate.equals(other.endorsementConfirmDate))
			return false;
		if (endorsementPaymentDate == null) {
			if (other.endorsementPaymentDate != null)
				return false;
		} else if (!endorsementPaymentDate.equals(other.endorsementPaymentDate))
			return false;
		if (Double.doubleToLongBits(fleetDiscount) != Double.doubleToLongBits(other.fleetDiscount))
			return false;
		if (fromTerm != other.fromTerm)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isOutstanding != other.isOutstanding)
			return false;
		if (isPO != other.isPO)
			return false;
		if (isReinstate != other.isReinstate)
			return false;
		if (isReverse != other.isReverse)
			return false;
		if (Double.doubleToLongBits(loanInterest) != Double.doubleToLongBits(other.loanInterest))
			return false;
		if (Double.doubleToLongBits(ncbPremium) != Double.doubleToLongBits(other.ncbPremium))
			return false;
		if (paymentChannel != other.paymentChannel)
			return false;
		if (paymentDate == null) {
			if (other.paymentDate != null)
				return false;
		} else if (!paymentDate.equals(other.paymentDate))
			return false;
		if (paymentType == null) {
			if (other.paymentType != null)
				return false;
		} else if (!paymentType.equals(other.paymentType))
			return false;
		if (Double.doubleToLongBits(penaltyPremium) != Double.doubleToLongBits(other.penaltyPremium))
			return false;
		if (poNo == null) {
			if (other.poNo != null)
				return false;
		} else if (!poNo.equals(other.poNo))
			return false;
		if (policyNo == null) {
			if (other.policyNo != null)
				return false;
		} else if (!policyNo.equals(other.policyNo))
			return false;
//		if (policyStatus != other.policyStatus)
//			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (Double.doubleToLongBits(rate) != Double.doubleToLongBits(other.rate))
			return false;
		if (receiptNo == null) {
			if (other.receiptNo != null)
				return false;
		} else if (!receiptNo.equals(other.receiptNo))
			return false;
		if (receivedDeno == null) {
			if (other.receivedDeno != null)
				return false;
		} else if (!receivedDeno.equals(other.receivedDeno))
			return false;
		if (referenceNo == null) {
			if (other.referenceNo != null)
				return false;
		} else if (!referenceNo.equals(other.referenceNo))
			return false;
//		if (referenceType != other.referenceType)
//			return false;
		if (Double.doubleToLongBits(refund) != Double.doubleToLongBits(other.refund))
			return false;
		if (refundDeno == null) {
			if (other.refundDeno != null)
				return false;
		} else if (!refundDeno.equals(other.refundDeno))
			return false;
		if (Double.doubleToLongBits(reinstatementPremium) != Double.doubleToLongBits(other.reinstatementPremium))
			return false;
		if (Double.doubleToLongBits(renewalInterest) != Double.doubleToLongBits(other.renewalInterest))
			return false;
		if (salesPoints == null) {
			if (other.salesPoints != null)
				return false;
		} else if (!salesPoints.equals(other.salesPoints))
			return false;
		if (Double.doubleToLongBits(servicesCharges) != Double.doubleToLongBits(other.servicesCharges))
			return false;
		if (Double.doubleToLongBits(stampFees) != Double.doubleToLongBits(other.stampFees))
			return false;
		if (toTerm != other.toTerm)
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
