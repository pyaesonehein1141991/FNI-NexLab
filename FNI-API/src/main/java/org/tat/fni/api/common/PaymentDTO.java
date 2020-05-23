package org.tat.fni.api.common;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.tat.fni.api.common.emumdata.PaymentChannel;
import org.tat.fni.api.common.interfaces.ISorter;
import org.tat.fni.api.common.utils.Utils;
import org.tat.fni.api.domain.Bank;
import org.tat.fni.api.domain.Payment;
import org.tat.fni.api.domain.PaymentType;
import org.tat.fni.api.domain.SalesPoints;


public class PaymentDTO implements ISorter {
	private static final long serialVersionUID = -2728650365142658238L;

	private String tempId;
	private String receiptNo;
	private String invoiceNo;
	private String bankAccountNo;
	private String chequeNo;
	private String referenceNo;
//	private PolicyReferenceType referenceType;
	private double basicPremium;
	private double addOnPremium;
	private double discountPercent;
	private Double servicesCharges;
	private double fleetDiscount;
	private double afpDiscountPercent;
	private double stampFees;
	private double administrationFees;
	private Double ncbPremium;
	private Double penaltyPremium;
	private String receivedDeno;
	private String refundDeno;
	private double claimAmount;
	private boolean complete;
	private PaymentChannel paymentChannel;
	private Bank bank;
	private SalesPoints salesPoints;
//	private PolicyStatus policyStatus;
	private double reinstatementPremium;
	private Date confirmDate;
	private Date paymentDate;
	private int fromTerm;
	private int toTerm;
	private PaymentType paymentType;
	private double refund;
	private double loanInterest;
	private double renewalInterest;
	private String poNo;
	private Bank accountBank;
	private boolean isOutstanding;
	private double salvageValue;
	private double medicalFees;

	public PaymentDTO() {
	}

	public PaymentDTO(List<Payment> paymentList) {
		if (paymentList != null && !paymentList.isEmpty()) {
			Payment tempPayment = paymentList.get(0);
			this.receiptNo = tempPayment.getReceiptNo();
			this.invoiceNo = tempPayment.getInvoiceNo();
			this.bankAccountNo = tempPayment.getBankAccountNo();
			this.salesPoints = tempPayment.getSalesPoints();
			this.referenceNo = tempPayment.getReferenceNo();
//			this.referenceType = tempPayment.getReferenceType();
			this.servicesCharges = tempPayment.getServicesCharges();
			this.stampFees = tempPayment.getStampFees();
			this.medicalFees = tempPayment.getMedicalFees();
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
			double basicPremium = 0.0;
			double addOnPremium = 0.0;
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
			this.discountPercent = Utils.getPercentOn(100, tempPayment.getSpecialDiscount(), this.basicPremium);
			this.afpDiscountPercent = Utils.getPercentOn(100, tempPayment.getAfpDiscount(), this.basicPremium);
			this.fleetDiscount = tempPayment.getFleetDiscount();
		}
	}

	public PaymentDTO(Payment tempPayment) {
		this.receiptNo = tempPayment.getReceiptNo();
		this.bankAccountNo = tempPayment.getBankAccountNo();
		this.salesPoints = tempPayment.getSalesPoints();
		this.referenceNo = tempPayment.getReferenceNo();
//		this.referenceType = tempPayment.getReferenceType();
		this.fleetDiscount = tempPayment.getFleetDiscount();
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
		this.discountPercent = Utils.getPercentOn(100, tempPayment.getSpecialDiscount(), this.basicPremium);
		this.afpDiscountPercent = Utils.getPercentOn(100, tempPayment.getAfpDiscount(), this.basicPremium);
		this.fleetDiscount = tempPayment.getFleetDiscount();
		this.medicalFees = tempPayment.getMedicalFees();
	}

	public boolean isOutstanding() {
		return isOutstanding;
	}

	public void setOutstanding(boolean isOutstanding) {
		this.isOutstanding = isOutstanding;
	}

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public SalesPoints getSalesPoints() {
		return salesPoints;
	}

	public void setSalesPoints(SalesPoints salesPoints) {
		this.salesPoints = salesPoints;
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
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

	public double getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(double discountPercent) {
		this.discountPercent = discountPercent;
	}

	public Double getServicesCharges() {
		if (servicesCharges == null) {
			servicesCharges = new Double(0.0);
		}
		return servicesCharges;
	}

	public void setServicesCharges(Double servicesCharges) {
		this.servicesCharges = servicesCharges;
	}

	public Number getServicesChargesNum() {
		if (servicesCharges == null) {
			servicesCharges = new Double(0.0);
		}
		return servicesCharges;
	}

	public void setServicesChargesNum(Number servicesCharges) {
		if (servicesCharges != null) {
			this.servicesCharges = servicesCharges.doubleValue();
		}
	}

	public double getStampFees() {
		return stampFees;
	}

	public void setStampFees(double stampFees) {
		this.stampFees = stampFees;
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

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
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

	public double getTotalPremium() {
		return basicPremium + addOnPremium + (penaltyPremium == null ? 0.0 : penaltyPremium);
	}

	public double getRenewalTotalPremium() {
		return (basicPremium - (ncbPremium == null ? 0.0 : ncbPremium)) + addOnPremium + (penaltyPremium == null ? 0.0 : penaltyPremium);
	}

	public double getBillCollectionTotalPremium() {
		return (basicPremium + addOnPremium);
	}

	public double getTotalDiscountAmount() {
		double result = getDiscountAmount() + getFleetDiscount() + getAfpDiscountAmount();
		return result;
	}

	public double getDiscountAmount() {
		double discountAmount = 0.0;
		if (discountPercent > 0.0) {
			discountAmount = basicPremium * discountPercent / 100;
		}
		return discountAmount;
	}

	public double getFleetDiscount() {
		return fleetDiscount;
	}

	public void setFleetDiscount(double fleetDiscount) {
		this.fleetDiscount = fleetDiscount;
	}

	public double getAfpDiscountAmount() {
		double discountAmount = 0.0;
		if (afpDiscountPercent > 0.0) {
			discountAmount = basicPremium * afpDiscountPercent / 100;
		}
		return discountAmount;
	}

	public double getNetPremium() {
		return getTotalPremium() - getTotalDiscountAmount() - getNcbPremiumNum().doubleValue();
	}

	public double getPersonTravelNetPremium() {
		double result = 0.0;
		result = basicPremium - getDiscountAmount();
		return result;
	}

	public double getRenewalNetPremium() {
		return getRenewalTotalPremium() - getTotalDiscountAmount();
	}

	public double getBillCollectionNetPremium() {
		return getBillCollectionTotalPremium() - getDiscountAmount();
	}

	public double getTotalAmount() {
		return getNetPremium() + getServicesCharges().doubleValue() + stampFees + administrationFees;
	}

	public double getPersonTravelTotalAmount() {
		return getPersonTravelNetPremium() + getServicesCharges().doubleValue() + stampFees + administrationFees;
	}

	public double getRenewalTotalAmount() {
		return getRenewalNetPremium() + servicesCharges.doubleValue() + stampFees + administrationFees;
	}

	public double getBillCollectionTotalAmount() {
		return (getBillCollectionNetPremium() + servicesCharges.doubleValue() + renewalInterest + loanInterest) - refund;
	}

	public double getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(double claimAmount) {
		this.claimAmount = claimAmount;
	}

	public double getTotalClaimAmount() {
		return getClaimAmount() - (servicesCharges.doubleValue() + reinstatementPremium + salvageValue);
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

	public double getAdministrationFees() {
		return administrationFees;
	}

	public void setAdministrationFees(double administrationFees) {
		this.administrationFees = administrationFees;
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

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public Bank getAccountBank() {
		return accountBank;
	}

	public void setAccountBank(Bank accountBank) {
		this.accountBank = accountBank;
	}

	public double getMedicalFees() {
		return medicalFees;
	}

	public void setMedicalFees(double medicalFees) {
		this.medicalFees = medicalFees;
	}

	public double getNcbPremium() {
		if (ncbPremium == null) {
			ncbPremium = new Double(0.0);
		}
		return ncbPremium;
	}

	public double getAfpDiscountPercent() {
		return afpDiscountPercent;
	}

	public void setAfpDiscountPercent(double afpDiscountPercent) {
		this.afpDiscountPercent = afpDiscountPercent;
	}

	public void setNcbPremium(double ncbPremium) {
		this.ncbPremium = ncbPremium;
	}

	public Number getNcbPremiumNum() {
		if (ncbPremium == null) {
			ncbPremium = new Double(0.0);
		}
		return ncbPremium;
	}

	public void setNcbPremiumNum(Number ncbPremium) {
		if (ncbPremium != null) {
			this.ncbPremium = ncbPremium.doubleValue();
		}
	}

	public double getPenaltyPremium() {
		if (penaltyPremium == null) {
			penaltyPremium = new Double(0.0);
		}
		return penaltyPremium;
	}

	public void setPenaltyPremium(double penaltyPremium) {
		this.penaltyPremium = penaltyPremium;
	}

	public Number getPenaltyPremiumNum() {
		if (penaltyPremium == null) {
			penaltyPremium = new Double(0.0);
		}
		return penaltyPremium;
	}

	public void setPenaltyPremiumNum(Number penaltyPremium) {
		if (ncbPremium != null) {
			this.penaltyPremium = penaltyPremium.doubleValue();
		}
	}

	public double getSalvageValue() {
		return salvageValue;
	}

	public void setSalvageValue(double salvageValue) {
		this.salvageValue = salvageValue;
	}

	@Override
	public boolean equals(Object object) {
		return EqualsBuilder.reflectionEquals(this, object);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String getRegistrationNo() {
		return receiptNo;
	}

}
