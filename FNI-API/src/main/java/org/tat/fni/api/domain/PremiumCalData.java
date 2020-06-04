package org.tat.fni.api.domain;

import java.io.Serializable;

public class PremiumCalData implements Serializable {
	private static final long serialVersionUID = 1L;

	private Double suminsured;
	private Double mainCoverSuminsured;
	private Double mainCoverPremium;
	private Double unit;

	public PremiumCalData(Double suminsured, Double mainCoverSuminsured, Double mainCoverPremium, Double unit) {
		this.suminsured = suminsured;
		this.mainCoverSuminsured = mainCoverSuminsured;
		this.mainCoverPremium = mainCoverPremium;
		this.unit = unit;
	}

	public Double getSuminsured() {
		return suminsured;
	}

	public void setSuminsured(Double suminsured) {
		this.suminsured = suminsured;
	}

	public Double getMainCoverSuminsured() {
		return mainCoverSuminsured;
	}

	public void setMainCoverSuminsured(Double mainCoverSuminsured) {
		this.mainCoverSuminsured = mainCoverSuminsured;
	}

	public Double getMainCoverPremium() {
		return mainCoverPremium;
	}

	public void setMainCoverPremium(Double mainCoverPremium) {
		this.mainCoverPremium = mainCoverPremium;
	}

	public Double getUnit() {
		return unit;
	}

	public void setUnit(Double unit) {
		this.unit = unit;
	}
}
