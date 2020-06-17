package org.tat.fni.api.dto.sportManDTO;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SportManPolicyDataDTO {
	
	@ApiModelProperty(position = 0, example = "FNI-001/SM/PO/0000000008/6-2020", required = true)
	@NotBlank(message = "ProposalNo is mandatory")
	private String proposalNo;

}
