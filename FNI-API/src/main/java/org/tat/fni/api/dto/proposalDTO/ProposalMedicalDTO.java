package org.tat.fni.api.dto.proposalDTO;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProposalMedicalDTO {
	
	@ApiModelProperty(position = 0, example = "FNI-HO/HI/PO/00000001/6-2019", required = true)
	@NotBlank(message = "ProposalNo is mandatory")
	private String proposalNo;

}
