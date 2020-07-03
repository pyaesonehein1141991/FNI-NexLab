package org.tat.fni.api.dto.shortTermEndowmentLifeDTO;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ShortTermPolicyDataDTO {

	@ApiModelProperty(position = 0, example = "FNI-HO/SL/PO/00000001/6-2019", required = true)
	@NotBlank(message = "ProposalNo is mandatory")
	private String proposalNo;

}