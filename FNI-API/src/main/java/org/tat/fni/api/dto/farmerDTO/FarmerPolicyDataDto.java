package org.tat.fni.api.dto.farmerDTO;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class FarmerPolicyDataDto {
	
	@ApiModelProperty(position = 0, example = "FNI-001/FA/PO/0000000010/6-2020", required = true)
	@NotBlank(message = "ProposalNo is mandatory")
	private String proposalNo;

}
