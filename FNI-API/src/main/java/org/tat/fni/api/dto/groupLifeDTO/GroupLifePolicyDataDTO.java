package org.tat.fni.api.dto.groupLifeDTO;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class GroupLifePolicyDataDTO {
	
	@ApiModelProperty(position = 0, example = "FNI-001/LG/PO/0000000003/5-2020", required = true)
	@NotBlank(message = "ProposalNo is mandatory")
	private String proposalNo;

}
