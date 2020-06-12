package org.tat.fni.api.dto.proposalDTO;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProposalLifeDTO {
	
	@ApiModelProperty(position = 0, example = "FNI-001/FA/PO/0000000010/6-2020", required = true)
	@NotBlank(message = "ProposalNo is mandatory")
	private String proposalNo;

}
