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

	@ApiModelProperty(position = 1, example = "36221e42-a0000040-133dc830-59dccb1a", required = true)
	@NotBlank(message = "BPMS InsuredPersonId is mandatory")
	private String bpmsInsuredPersonId;

	@ApiModelProperty(position = 2, example = "100000", required = true)
	@NotNull(message = "proposedSumInsured is mandatory")
	private double proposedSumInsured;

	@ApiModelProperty(position = 3, example = "100000", required = true)
	@NotNull(message = "proposedPremium is mandatory")
	private double proposedPremium;

	@ApiModelProperty(position = 4, example = "10000", required = true)
	@NotNull(message = "approvedSumInsured is mandatory")
	private double approvedSumInsured;

	@ApiModelProperty(position = 5, example = "10000", required = true)
	@NotNull(message = "approvedPremium is mandatory")
	private double approvedPremium;

	@ApiModelProperty(position = 6, example = "10000", required = true)
	@NotNull(message = "basicTermPremium is mandatory")
	private double basicTermPremium;

	@ApiModelProperty(position = 7, example = "FRCNO", required = true)
	@NotNull(message = "idType is mandatory")
	private String idType;

	@ApiModelProperty(position = 8, example = "123123123")
	private String idNo;

	@ApiModelProperty(position = 9, example = "U Maung Maung", required = true)
	@NotBlank(message = "fatherName is mandatory")
	private String fatherName;

	@ApiModelProperty(position = 10, example = "2019-12-16", required = true)
	@NotNull(message = "startDate is mandatory")
	@JsonDeserialize(using = DateHandler.class)
	private Date startDate;

	@ApiModelProperty(position = 11, example = "2019-12-16", required = true)
	@NotNull(message = "endDate is mandatory")
	@JsonDeserialize(using = DateHandler.class)
	private Date endDate;

	@ApiModelProperty(position = 12, example = "1999-12-16", required = true)
	@NotNull(message = "dateOfBirth is mandatory")
	@JsonDeserialize(using = DateHandler.class)
	private Date dateOfBirth;

	@ApiModelProperty(position = 14, example = "MALE", required = true)
	@NotNull(message = "gender is mandatory")
	private String gender;

	@ApiModelProperty(position = 15, example = "Yangon", required = true)
	@NotNull(message = "residentAddress is mandatory")
	private String residentAddress;

	@ApiModelProperty(position = 16, example = "AUNG ", required = true)
	@NotNull(message = "firstName is mandatory")
	private String firstName;

	@ApiModelProperty(position = 17, example = "AUNG")
	private String middleName;

	@ApiModelProperty(position = 18, example = "AUNG")
	private String lastName;

	@ApiModelProperty(position = 22, example = "ISSYS004000009724620062019", required = true)
	@NotBlank(message = "townshipId is mandatory")
	private String townshipId;

	@ApiModelProperty(position = 23, example = "ISSYS0120001000000000129032013", required = true)
	@NotBlank(message = "relationshipId is mandatory")
	private String relationshipId;

	@ApiModelProperty(position = 24, example = "ISSCH001000000000111232019")
	private String schoolId;

	@ApiModelProperty(position = 25, example = "ISSYS048000000000107082019")
	private String gradeInfo;

	@ApiModelProperty(position = 26, example = "Daw Hla Hla", required = true)
	@NotBlank(message = "parentName is mandatory")
	private String motherName;

	@ApiModelProperty(position = 27, example = "FRCNO")
	private String motherIdType;

	@ApiModelProperty(position = 28, example = "1628943")
	private String motherIdNo;

	@ApiModelProperty(position = 29, example = "1999-12-16")
	@JsonDeserialize(using = DateHandler.class)
	private Date motherDOB;

}
