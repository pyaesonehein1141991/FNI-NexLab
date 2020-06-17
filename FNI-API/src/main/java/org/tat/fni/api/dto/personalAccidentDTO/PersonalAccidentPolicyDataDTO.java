package org.tat.fni.api.dto.personalAccidentDTO;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PersonalAccidentPolicyDataDTO {
	
	@ApiModelProperty(position = 0, example = "FNI-001/PA/PO/0000000004/5-2020", required = true)
	@NotBlank(message = "ProposalNo is mandatory")
	private String proposalNo;

}
