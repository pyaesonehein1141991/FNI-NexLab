package org.tat.fni.api.dto.groupFarmerDTO;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.tat.fni.api.configuration.DateHandler;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FarmerProposalInsuredPersonDTO {

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
	
	@ApiModelProperty(position = 4, example = "100000", required = true)
	@NotNull(message = "proposedPremium is mandatory")
	private double proposedPremium;

	@ApiModelProperty(position = 5, example = "10000", required = true)
	@NotNull(message = "approvedSumInsured is mandatory")
	private double approvedSumInsured;

	@ApiModelProperty(position = 6, example = "10000", required = true)
	@NotNull(message = "approvedPremium is mandatory")
	private double approvedPremium;

	@ApiModelProperty(position = 7, example = "10000", required = true)
	@NotNull(message = "basicTermPremium is mandatory")
	private double basicTermPremium;
	
	@ApiModelProperty(position = 8, example = "U Maung Maung", required = true)
	@NotBlank(message = "fatherName is mandatory")
	private String fatherName;
	
	@ApiModelProperty(position = 9, example = "1999-12-16", required = true)
	@NotNull(message = "dateOfBirth is mandatory")
	@JsonDeserialize(using = DateHandler.class)
	private Date dateOfBirth;

	@ApiModelProperty(position = 10, example = "MALE", required = true)
	@NotNull(message = "gender is mandatory")
	private String gender;
	
	@ApiModelProperty(position = 11, example = "NRCNO", required = true)
	@NotNull(message = "idType is mandatory")
	private String idType;

	@ApiModelProperty(position = 12, example = "123123123")
	private String idNo;
	
	@ApiModelProperty(position = 13, example = "Yangon", required = true)
	@NotNull(message = "residentAddress is mandatory")
	private String residentAddress;
	
	@ApiModelProperty(position = 14, example = "ISSYS004000009724620062019", required = true)
	@NotBlank(message = "townshipId is mandatory")
	@NotEmpty
	private String townshipId;
}
