package org.tat.fni.api.dto.retrieveDTO.policyData;

import java.util.Date;

import org.tat.fni.api.common.emumdata.Gender;
import org.tat.fni.api.dto.retrieveDTO.NameDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InsuredPersonData {

	private double proposedSumInsured;

	private double proposedPremium;

	private String idNo;

	private String fatherName;

	private Date dateOfBirth;

	private Gender gender;

	private String address;

	private NameDto name;

	private String schoolName;

	private String gradeInfo;

	private String motherName;

	private String motherIdNo;

	private String motherDOB;

}
