package org.tat.fni.api.dto.publicTermLife;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.tat.fni.api.configuration.DateHandler;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PublicTermInsuredPersonBeneficiaryDTO {

	 @ApiModelProperty(position = 0, example = "U", required = true)
	  @NotBlank(message = "InitialId is mandatory")
	  private String initialId;

	  @ApiModelProperty(position = 1, example = "2019-12-16", required = true)
	  @NotNull(message = "dob is mandatory")
	  @JsonDeserialize(using = DateHandler.class)
	  private Date dob;

	  @ApiModelProperty(position = 2, example = "5", required = true)
	  @NotNull(message = "percentage is mandatory")
	  private float percentage;

	  @ApiModelProperty(position = 3, example = "NRCNO", required = true)
	  @NotBlank(message = "idType is mandatory")
	  private String idType;

	  @ApiModelProperty(position = 4, example = "123123123", required = true)
	  private String idNo;

	  @ApiModelProperty(position = 5, example = "MALE", required = true)
	  @NotNull(message = "gender is mandatory")
	  private String gender;

	  @ApiModelProperty(position = 6, example = "Yangon", required = true)
	  @NotBlank(message = "residentAddress is mandatory")
	  private String residentAddress;

	  @ApiModelProperty(position = 11, example = "ISSYS004002000000127620082014", required = true)
	  @NotBlank(message = "townshipId is mandatory")
	  @NotEmpty
	  private String townshipId;

	  @ApiModelProperty(position = 7, example = "Aung", required = true)
	  @NotBlank(message = "firstName is mandatory")
	  private String firstName;

	  @ApiModelProperty(position = 8, example = "Aung", required = true)
	  private String middleName;

	  @ApiModelProperty(position = 9, example = "Aung", required = true)
	  private String lastName;

	  @ApiModelProperty(position = 10, example = "ISSYS0120001000000000129032013", required = true)
	  @NotBlank(message = "relationshipID is mandatory")
	  private String relationshipID;

}
