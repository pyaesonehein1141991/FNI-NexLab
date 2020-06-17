package org.tat.fni.api.dto.retrieveDTO.policyData;

import java.util.Date;

import org.tat.fni.api.dto.retrieveDTO.NameDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InsuredPersonMedicalData {
	
	private double proposedSumInsured;

	private double proposedPremium;

	private String idNo;

	private String fatherName;

	private Date dateOfBirth;

	private String address;

	private String name;

	private String schoolName;

	private String gradeInfo;

	private String motherName;

	private String motherIdNo;

	private String motherDOB;

}
