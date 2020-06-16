package org.tat.fni.api.dto.retrieveDTO.policyData;

import java.util.Date;

import org.tat.fni.api.common.emumdata.Gender;
import org.tat.fni.api.dto.retrieveDTO.NameDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerData {
	
	private NameDto name;
	
	private String idNo;
	
	private Date dateOfBirth;
	
	private String address;
	
	private String phoneNo;
	
	private String fatherName;

	private Gender gender;

	private String occupation;
}
