package org.tat.fni.api.dto.studentLifeDTO;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class StudentLifePolicyDataDTO {
	
	@ApiModelProperty(position = 0, example = "FNI-001/SP/0000000038/6-2020", required = true)
	@NotBlank(message = "ProposalNo is mandatory")
	private String proposalNo;

}
