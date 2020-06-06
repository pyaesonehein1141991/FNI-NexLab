package org.tat.fni.api.dto.sportManDTO;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.tat.fni.api.configuration.DateHandler;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SportManProposalInsuredPersonDTO {

	@ApiModelProperty(position = 0, example = "ISSYS001001000000000103062019")
	private String customerID;

	@ApiModelProperty(position = 1, example = "U", required = true)
	@NotBlank(message = "InitialId is mandatory")
	private String initialId;

	@ApiModelProperty(position = 2, example = "AUNG ", required = true)
	@NotNull(message = "firstName is mandatory")
	private String firstName;

	@ApiModelProperty(position = 3, example = "AUNG")
	private String middleName;

	@ApiModelProperty(position = 4, example = "AUNG")
	private String lastName;

	@ApiModelProperty(position = 5, example = "U Maung Maung", required = true)
	@NotBlank(message = "fatherName is mandatory")
	private String fatherName;

	@ApiModelProperty(position = 6, example = "1999-12-16", required = true)
	@NotNull(message = "dateOfBirth is mandatory")
	@JsonDeserialize(using = DateHandler.class)
	private Date dateOfBirth;

	@ApiModelProperty(position = 7, example = "099876543")
	private String phone;

	@ApiModelProperty(position = 8, example = "NRCNO", required = true)
	@NotNull(message = "idType is mandatory")
	private String idType;

	@ApiModelProperty(position = 9, example = "123123123")
	private String idNo;

	@ApiModelProperty(position = 10, example = "ISSYS0120001000000000129032013", required = true)
	@NotBlank(message = "relationshipId is mandatory")
	@NotEmpty
	private String relationshipId;

	@ApiModelProperty(position = 11, example = "Yangon", required = true)
	@NotNull(message = "residentAddress is mandatory")
	private String residentAddress;

	@ApiModelProperty(position = 12, example = "ISSYS004001000000731326012017", required = true)
	@NotBlank(message = "townshipId is mandatory")
	@NotEmpty
	private String townshipId;

	@ApiModelProperty(position = 13, example = "ISSYS011000009823001042019", required = true)
	private String occupationID;

	@ApiModelProperty(position = 14, example = "MALE", required = true)
	@NotNull(message = "gender is mandatory")
	private String gender;

	@ApiModelProperty(position = 16, example = "100000", required = true)
	@NotNull(message = "proposedPremium is mandatory")
	private double proposedPremium;

	// approve

	@ApiModelProperty(position = 17, example = "5", required = true)
	@NotNull(message = "approvedUnit is mandatory")
	private int approvedUnit;

	@ApiModelProperty(position = 18, example = "ISSYSO52001000000000123052019", required = true)
	private String riskoccupationID;

	@ApiModelProperty(position = 19, example = "3", required = true)
	@NotNull(message = "unit is mandatory")
	private int unit;

	// needMedicalCheckup

	// rejectReason

	@ApiModelProperty(position = 20, example = "ISSYS044001000000000121062019", required = true)
	@NotBlank(message = "typeofSportId is mandatory")
	@NotEmpty
	private String typeofSportId;

	@ApiModelProperty(position = 21, required = true)
	@NotNull(message = "insuredPersonBeneficiariesList is mandatory")
	private List<SportManInsuredPersonBeneficiariesDTO> insuredPersonBeneficiariesList;

	// @Valid
	// @ApiModelProperty(position = 22)
	// @NotNull(message = "insuredPersonBeneficiariesList is mandatory")
	// private List<SportManinsuredPersonPolicyHistoryRecordDTO>
	// insuredPersonPolicyHistoryRecordList;

}
