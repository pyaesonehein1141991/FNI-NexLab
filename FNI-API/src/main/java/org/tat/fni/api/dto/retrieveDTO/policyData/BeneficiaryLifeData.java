package org.tat.fni.api.dto.retrieveDTO.policyData;

import java.util.Date;

import org.tat.fni.api.dto.retrieveDTO.NameDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BeneficiaryLifeData {
	
	private NameDto name;

	private Date dateOfBirth;

	private String phoneNo;

	private String idNo;

	private String relationship;

	private float percentage;

	private String address;

}
