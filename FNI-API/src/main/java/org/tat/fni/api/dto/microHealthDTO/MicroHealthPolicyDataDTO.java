package org.tat.fni.api.dto.microHealthDTO;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class MicroHealthPolicyDataDTO {
	
	@ApiModelProperty(position = 0, example = "FNI-HO/HI/PO/00000056/6-2019", required = true)
	@NotBlank(message = "ProposalNo is mandatory")
	private String proposalNo;

}
