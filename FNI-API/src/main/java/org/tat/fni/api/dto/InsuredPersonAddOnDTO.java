package org.tat.fni.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InsuredPersonAddOnDTO {
	
	@ApiModelProperty(position = 0, example = "3", required = true)
	@NotNull(message = "unit is mandatory")
	private int unit;
	
	@ApiModelProperty(position = 1, example = "26400", required = true)
	@NotNull(message = "premium is mandatory")
	private double premium;
	
	@ApiModelProperty(position = 2, example = "ISSYS014001000009603323042019", required = true)
	@NotBlank(message = "medicalProductAddOnId is mandatory")
	@NotEmpty
	private String medicalProductAddOnId;

}
