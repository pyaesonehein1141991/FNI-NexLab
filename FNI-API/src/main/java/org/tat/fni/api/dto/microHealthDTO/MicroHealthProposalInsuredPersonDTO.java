package org.tat.fni.api.dto.microHealthDTO;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.tat.fni.api.common.emumdata.Gender;
import org.tat.fni.api.common.emumdata.IdType;
import org.tat.fni.api.configuration.DateHandler;
import org.tat.fni.api.dto.InsuredPersonAddOnDTO;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MicroHealthProposalInsuredPersonDTO {
	
	@ApiModelProperty(position = 0, example = "U", required = true)
	@NotBlank(message = "InitialId is mandatory")
	private String initialId;

	@ApiModelProperty(position = 1, example = "23", required = true)
	@NotNull(message = "age is mandatory")
	private int age;
	
	@ApiModelProperty(position = 2, example = "Aung", required = true)
	@NotBlank(message = "firstName is mandatory")
	private String firstName;

	@ApiModelProperty(position = 3, example = "Aung", required = true)
	private String middleName;

	@ApiModelProperty(position = 4, example = "Aung", required = true)
	private String lastName;

	@ApiModelProperty(position = 5, example = "5", required = true)
	@NotNull(message = "unit is mandatory")
	private int unit;
	
	@ApiModelProperty(position = 6, example = "U Maung Maung", required = true)
	@NotBlank(message = "fatherName is mandatory")
	private String fatherName;

	@ApiModelProperty(position = 7, example = "true")
	private boolean needMedicalCheckup;

	@ApiModelProperty(position = 8, example = "1999-12-16", required = true)
	@NotNull(message = "dateOfBirth is mandatory")
	@JsonDeserialize(using = DateHandler.class)
	private Date dateOfBirth;

	@ApiModelProperty(position = 10, example = "ISSYS0120001000000000129032013", required = true)
	@NotBlank(message = "relationshipId is mandatory")
	@NotEmpty
	private String relationshipId;

	@ApiModelProperty(position = 11, example = "ISMED023001000000000110062019")
	private String guardianId;
	
	@ApiModelProperty(position = 12, example = "NRCNO", required = true)
	@NotBlank(message = "idType is mandatory")
	private IdType idType;

	@ApiModelProperty(position = 13, example = "098166", required = true)
	@NotBlank(message = "idType is mandatory")
	private String idNo;

	@ApiModelProperty(position = 14, example = "Yangon", required = true)
	@NotBlank(message = "residentAddress is mandatory")
	private String residentAddress;

	@ApiModelProperty(position = 15, example = "ISSYS004001000000731326012017", required = true)
	@NotBlank(message = "townshipId is mandatory")
	@NotEmpty
	private String townshipId;

	@ApiModelProperty(position = 16, example = "ISSYS011000009823001042019", required = true)
	@NotBlank(message = "occupationID is mandatory")
	@NotEmpty
	private String occupationID;

	@ApiModelProperty(position = 17, example = "MALE", required = true)
	@NotBlank(message = "gender is mandatory")
	private Gender gender;

	@ApiModelProperty(position = 18, example = "1000000", required = true)
	@NotBlank(message = "proposedSumInsured is mandatory")
	private double proposedSumInsured;

	@ApiModelProperty(position = 19, example = "1000000", required = true)
	@NotBlank(message = "proposedPremium is mandatory")
	private double proposedPremium;

	@Valid
	@ApiModelProperty(position = 20)
	@NotNull(message = "insuredPersonBeneficiariesList is mandatory")
	private List<MicroHealthProposalInsuredPersonBeneficiariesDTO> insuredPersonBeneficiariesList;

}
