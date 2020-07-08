package org.tat.fni.api.dto.endowmentLifeDTO;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.tat.fni.api.common.emumdata.Gender;
import org.tat.fni.api.configuration.DateHandler;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EndowmentLifeProposalInsuredPersonBeneficiariesDTO {
	
	@ApiModelProperty(position = 0, example = "U", required = true)
	@NotBlank(message = "InitialId is mandatory")
	private String initialId;

	@ApiModelProperty(position = 1, example = "Aung", required = true)
	@NotBlank(message = "firstName is mandatory")
	private String firstName;

	@ApiModelProperty(position = 2, example = "Aung", required = true)
	private String middleName;

	@ApiModelProperty(position = 3, example = "Aung", required = true)
	private String lastName;
	
	@ApiModelProperty(position = 4, example = "MALE", required = true)
	@NotBlank(message = "gender is mandatory")
	private String gender;

	@ApiModelProperty(position = 5, example = "0996543423", required = true)
	@NotNull(message = "phone is mandatory")
	private String phone;
	
	@ApiModelProperty(position = 6, example = "NRCNO", required = true)
	@NotBlank(message = "idType is mandatory")
	private String idType;

	@ApiModelProperty(position = 7, example = "098166", required = true)
	@NotBlank(message = "idType is mandatory")
	private String idNo;

	@ApiModelProperty(position = 8, example = "ISSYS0120001000000000129032013      ", required = true)
	@NotBlank(message = "relationship is mandatory")
	private String relationshipId;

	@ApiModelProperty(position = 9, example = "5", required = true)
	@NotNull(message = "percentage is mandatory")
	private float percentage;
	
	@ApiModelProperty(position = 10, example = "23")
	private int age;

	@ApiModelProperty(position = 11, example = "Yangon", required = true)
	@NotBlank(message = "residentAddress is mandatory")
	private String residentAddress;

	@ApiModelProperty(position = 12, example = "ISSYS004001000000731326012017", required = true)
	@NotBlank(message = "townshipId is mandatory")
	@NotEmpty
	private String townshipId;

}
