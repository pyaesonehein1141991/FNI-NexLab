package org.tat.fni.api.dto;

import java.util.Date;

import lombok.Data;

@Data
public class SportManAbroadDTO {
	
	private String fromCity;
	
	private String toCity;
	
	private double premium;
	
	private Date travelStartDate;
	
	private Date travelEndDate;
	
	private String policyInsuredPersonId;
	
//	private List<policyInsuredPersonAddOnList> policyInsuredPersonAddOnList;
	
	private double sumInsured;
	
	private String ProductAddonId;


}
