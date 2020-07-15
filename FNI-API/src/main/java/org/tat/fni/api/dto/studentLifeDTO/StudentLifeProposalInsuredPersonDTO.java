package org.tat.fni.api.dto.studentLifeDTO;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.tat.fni.api.configuration.DateHandler;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StudentLifeProposalInsuredPersonDTO {

	@ApiModelProperty(position = 0, example = "U", required = true)
	@NotBlank(message = "InitialId is mandatory")
	private String initialId;

	@ApiModelProperty(position = 1, example = "AUNG ", required = true)
	@NotNull(message = "firstName is mandatory")
	private String firstName;

	@ApiModelProperty(position = 2, example = "AUNG")
	private String middleName;

	@ApiModelProperty(position = 3, example = "AUNG")
	private String lastName;

	@ApiModelProperty(position = 4, example = "U Maung Maung", required = true)
	@NotBlank(message = "fatherName is mandatory")
	private String fatherName;

	@ApiModelProperty(position = 5, example = "1999-12-16", required = true)
	@NotNull(message = "dateOfBirth is mandatory")
	@JsonDeserialize(using = DateHandler.class)
	private Date dateOfBirth;

	@ApiModelProperty(position = 6, example = "098888888")
	private String phone;

	@ApiModelProperty(position = 7, example = "NRCNO", required = true)
	@NotNull(message = "idType is mandatory")
	private String idType;

	@ApiModelProperty(position = 8, example = "123123123", required = true)
	@NotNull(message = "idNo is mandatory")
	private String idNo;

	@ApiModelProperty(position = 9, example = "10", required = true)
	@NotNull(message = "age is mandatory")
	private int age;

	@ApiModelProperty(position = 10, example = "ISSYS0120001000000000129032013", required = true)
	@NotBlank(message = "relationshipId is mandatory")
	private String relationshipId;

	@ApiModelProperty(position = 11, example = "Yangon", required = true)
	@NotNull(message = "residentAddress is mandatory")
	private String residentAddress;

	@ApiModelProperty(position = 12, example = "ISSYS004001000000731326012017", required = true)
	@NotBlank(message = "townshipId is mandatory")
	private String townshipId;

	@ApiModelProperty(position = 13, example = "MALE", required = true)
	@NotNull(message = "gender is mandatory")
	private String gender;

	@ApiModelProperty(position = 14, example = "100000", required = true)
	@NotNull(message = "proposedSumInsured is mandatory")
	private double proposedSumInsured;

	@ApiModelProperty(position = 15, example = "some reason")
	private String rejectReason;

	@ApiModelProperty(position = 16, example = "false", required = true)
	@NotNull(message = "needMedicalCheckup is mandatory")
	private boolean needMedicalCheckup;

	@ApiModelProperty(position = 17, example = "100000", required = true)
	@NotNull(message = "proposedPremium is mandatory")
	private double proposedPremium;

	@ApiModelProperty(position = 18, example = "10000", required = true)
	@NotNull(message = "approvedSumInsured is mandatory")
	private double approvedSumInsured;

	@ApiModelProperty(position = 19, example = "10000", required = true)
	@NotNull(message = "basicTermPremium is mandatory")
	private double basicTermPremium;

	@ApiModelProperty(position = 20, example = "true", required = true)
	@NotNull(message = "approve is mandatory")
	private boolean approve;

	@ApiModelProperty(position = 21, example = "U Maung Maung", required = true)
	@NotNull(message = "parentName is mandatory")
	private String parentName;

	@ApiModelProperty(position = 22, example = "NRCNO", required = true)
	@NotNull(message = "parentIdType is mandatory")
	private String parentIdType;

	@ApiModelProperty(position = 23, example = "123123123", required = true)
	@NotNull(message = "parentIdNo is mandatory")
	private String parentIdNo;

	@ApiModelProperty(position = 24, example = "1999-12-16")
	@JsonDeserialize(using = DateHandler.class)
	private Date parentDOB;

	@ApiModelProperty(position = 25, example = "11", required = true)
	@NotBlank(message = "grateInfoId is mandatory")
	private String grateInfoId;

	@ApiModelProperty(position = 26, example = "ISSCH001001000000000117032020", required = true)
	@NotBlank(message = "schoolId is mandatory")
	private String schoolId;

}
