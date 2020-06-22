package org.tat.fni.api.dto.publicLifeDTO;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.tat.fni.api.common.emumdata.Gender;
import org.tat.fni.api.configuration.DateHandler;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PublicLifeProposalInsuredPersonDTO {
	
	@ApiModelProperty(position = 0, example = "U", required = true)
	@NotBlank(message = "InitialId is mandatory")
	private String initialId;

	@ApiModelProperty(position = 1, example = "23", required = true)
	@NotNull(message = "age is mandatory")
	private int age;
	
	@ApiModelProperty(position = 2, example = "100000", required = true)
	@NotBlank(message = "proposedSumInsured is mandatory")
	private double proposedSumInsured;
	
	@ApiModelProperty(position = 3, example = "100000", required = true)
	@NotBlank(message = "proposedPremium is mandatory")
	private double proposedPremium;
	
	@ApiModelProperty(position = 4, example = "1999-12-16", required = true)
	@NotNull(message = "dateOfBirth is mandatory")
	@JsonDeserialize(using = DateHandler.class)
	private Date dateOfBirth;
	
	@ApiModelProperty(position = 5, example = "U Maung Maung", required = true)
	@NotBlank(message = "fatherName is mandatory")
	private String fatherName;
	
	@ApiModelProperty(position = 6, example = "MALE", required = true)
	@NotBlank(message = "gender is mandatory")
	private String gender;
	
	@ApiModelProperty(position = 7, example = "098166", required = true)
	@NotBlank(message = "idType is mandatory")
	private String idNo;
	
	@ApiModelProperty(position = 8, example = "NRCNO", required = true)
	@NotBlank(message = "idType is mandatory")
	private String idType;
	
	@ApiModelProperty(position = 9, example = "Aung", required = true)
	@NotBlank(message = "firstName is mandatory")
	private String firstName;

	@ApiModelProperty(position = 10, example = "Aung", required = true)
	private String middleName;

	@ApiModelProperty(position = 11, example = "Aung", required = true)
	private String lastName;
	
	@ApiModelProperty(position = 12, example = "Yangon", required = true)
	@NotBlank(message = "residentAddress is mandatory")
	private String residentAddress;
	
	@ApiModelProperty(position = 13, example = "ISSYS011000009823001042019", required = true)
	@NotBlank(message = "occupationID is mandatory")
	@NotEmpty
	private String occupationID;

	@Valid
	@ApiModelProperty(position = 14)
	@NotNull(message = "insuredPersonBeneficiariesList is mandatory")
	private List<PublicLifeProposalInsuredPersonBeneficiariesDTO> insuredPersonBeneficiariesList;

}
