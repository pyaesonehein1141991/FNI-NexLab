package org.tat.fni.api.dto.retrieveDTO.policyData;

import java.util.List;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PolicyDataCriteria {
	
	@ApiModelProperty(
			position = 0, example = "FNI-HO/SL/PO/00000001/6-2019", required = true)
	@NotBlank(message = "ProposalNo is mandatory")
	private String proposalNo;

//	@ApiModelProperty(position = 0, required = true)
//	@NotBlank(message = "ProposalNo is mandatory")
//	private List<FarmerPolicyDto> proposalNoList;

}
