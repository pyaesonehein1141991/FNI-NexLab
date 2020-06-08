package org.tat.fni.api.dto.sportManDTO;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SportManAbroadDTO {

	@ApiModelProperty(position = 0, required = false)
	private String fromCity;

	@ApiModelProperty(position = 1, required = false)
	private String toCity;

	@ApiModelProperty(position = 2, required = true)
	@NotBlank(message = "Agent ID is mandatory")
	private double premium;

	@ApiModelProperty(position = 3, required = true)
	@NotBlank(message = "Agent ID is mandatory")
	private Date travelStartDate;

	@ApiModelProperty(position = 4, required = true)
	@NotBlank(message = "Agent ID is mandatory")
	private Date travelEndDate;

	@ApiModelProperty(position = 5, required = true)
	@NotBlank(message = "Agent ID is mandatory")
	private String policyInsuredPersonId;

	@ApiModelProperty(position = 6, required = true)
	@NotBlank(message = "Agent ID is mandatory")
	private double sumInsured;

	@ApiModelProperty(position = 7, required = true)
	@NotBlank(message = "Agent ID is mandatory")
	private String ProductAddonId;

}
