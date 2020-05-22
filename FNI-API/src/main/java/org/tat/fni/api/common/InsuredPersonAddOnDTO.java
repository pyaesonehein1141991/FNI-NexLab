package org.tat.fni.api.common;

import org.tat.fni.api.domain.InsuredPersonAddon;
import org.tat.fni.api.domain.addon.AddOn;

public class InsuredPersonAddOnDTO {
	private boolean existEntity;
	private String tempId;
	private double addOnSumInsured;
	private double premium;
	private double approvedPremium;
	private double approvedSumInsured;
	private AddOn addOn;
	private int version;

	public InsuredPersonAddOnDTO() {
		tempId = System.currentTimeMillis() + "";
	}

	public InsuredPersonAddOnDTO(AddOn addOn, double addOnSumInsured) {
		tempId = addOn.getId();
		this.addOnSumInsured = addOnSumInsured;
		this.addOn = addOn;
	}

	public InsuredPersonAddOnDTO(InsuredPersonAddon addOn) {
		if (addOn.getId() == null) {
			this.tempId = System.nanoTime() + "";
		} else {
			existEntity = true;
			this.tempId = addOn.getId();
			this.version = addOn.getVersion();
		}
		this.addOnSumInsured = addOn.getProposedSumInsured();
		this.addOn = addOn.getAddOn();
		this.approvedPremium = addOn.getApprovedPremium();
		this.approvedSumInsured = addOn.getApprovedSumInsured();
	}

	public InsuredPersonAddOnDTO(InsuredPersonAddOnDTO addOn) {
		this.tempId = System.nanoTime() + "";
		existEntity = true;
		this.version = addOn.getVersion();
		this.addOnSumInsured = addOn.getAddOnSumInsured();
		this.addOn = addOn.getAddOn();
		this.approvedPremium = addOn.getApprovedPremium();
		this.approvedSumInsured = addOn.getApprovedSumInsured();
	}

	//commented methods related to policy 
	
//	public InsuredPersonAddOnDTO(PolicyInsuredPersonAddon addOn) {
//		this.addOnSumInsured = addOn.getSumInsured();
//		this.premium = addOn.getPremium();
//		this.addOn = addOn.getAddOn();
//	}

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	public double getAddOnSumInsured() {
		return addOnSumInsured;
	}

	public void setAddOnSumInsured(double addOnSumInsured) {
		this.addOnSumInsured = addOnSumInsured;
	}

	public AddOn getAddOn() {
		return addOn;
	}

	public void setAddOn(AddOn addOn) {
		this.addOn = addOn;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public double getApprovedPremium() {
		return approvedPremium;
	}

	public void setApprovedPremium(double approvedPremium) {
		this.approvedPremium = approvedPremium;
	}

	public double getApprovedSumInsured() {
		return approvedSumInsured;
	}

	public void setApprovedSumInsured(double approvedSumInsured) {
		this.approvedSumInsured = approvedSumInsured;
	}

	public boolean isExistEntity() {
		return existEntity;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}
