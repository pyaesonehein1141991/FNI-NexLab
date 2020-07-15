package org.tat.fni.api.dto.groupLifeDTO;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.tat.fni.api.configuration.DateHandler;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GroupLifeProposalInsuredPersonDTO {

	@ApiModelProperty(position = 0, example = "23", required = true)
	@NotNull(message = "age is mandatory")
	private int age;

	@ApiModelProperty(position = 2, example = "U", required = true)
	@NotBlank(message = "InitialId is mandatory")
	private String initialId;

	@ApiModelProperty(position = 3, example = "AUNG ", required = true)
	@NotNull(message = "firstName is mandatory")
	private String firstName;

	@ApiModelProperty(position = 4, example = "AUNG")
	private String middleName;

	@ApiModelProperty(position = 5, example = "AUNG")
	private String lastName;

	@ApiModelProperty(position = 6, example = "U Maung Maung", required = true)
	@NotBlank(message = "fatherName is mandatory")
	private String fatherName;

	@ApiModelProperty(position = 7, example = "1999-12-16", required = true)
	@NotNull(message = "dateOfBirth is mandatory")
	@JsonDeserialize(using = DateHandler.class)
	private Date dateOfBirth;

	@ApiModelProperty(position = 8, example = "098888888")
	private String phone;

	@ApiModelProperty(position = 9, example = "NRCNO", required = true)
	@NotNull(message = "idType is mandatory")
	private String idType;

	@ApiModelProperty(position = 10, example = "123123123", required = true)
	@NotNull(message = "idNo is mandatory")
	private String idNo;

	@ApiModelProperty(position = 11, example = "ISSYS0120001000000000129032013", required = true)
	@NotBlank(message = "relationshipId is mandatory")
	private String relationshipId;

	@ApiModelProperty(position = 12, example = "Yangon", required = true)
	@NotNull(message = "residentAddress is mandatory")
	private String residentAddress;

	@ApiModelProperty(position = 13, example = "ISSYS004001000000731326012017", required = true)
	@NotBlank(message = "townshipId is mandatory")
	private String townshipId;

	@ApiModelProperty(position = 14, example = "ISSYS011000009823001042019", required = true)
	@NotBlank(message = "occupationID is mandatory")
	private String occupationID;

	@ApiModelProperty(position = 15, example = "MALE", required = true)
	@NotNull(message = "gender is mandatory")
	private String gender;

	@ApiModelProperty(position = 16, example = "100000", required = true)
	@NotNull(message = "proposedSumInsured is mandatory")
	private double proposedSumInsured;

	@ApiModelProperty(position = 19, example = "100000", required = true)
	@NotNull(message = "proposedPremium is mandatory")
	private double proposedPremium;

	@ApiModelProperty(position = 22, required = true)
	@NotNull(message = "weight is mandatory")
	private int weight;

	@ApiModelProperty(position = 23, required = true)
	@NotNull(message = "height is mandatory")
	private int height;

	@Valid
	@ApiModelProperty(position = 24)
	@NotNull(message = "insuredPersonBeneficiariesList is mandatory")
	private List<GroupLifeProposalInsuredPersonBeneficiariesDTO> insuredPersonBeneficiariesList;

}
