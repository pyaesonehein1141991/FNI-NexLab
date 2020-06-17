package org.tat.fni.api.dto.snakeBiteDTO;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SnakeBitePolicyDataDTO {
	
	@ApiModelProperty(position = 0, example = "FNI-001/SB/PO/0000000002/5-2020", required = true)
	@NotBlank(message = "ProposalNo is mandatory")
	private String proposalNo;

}
