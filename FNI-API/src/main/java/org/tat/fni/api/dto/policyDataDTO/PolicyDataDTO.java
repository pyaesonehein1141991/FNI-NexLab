package org.tat.fni.api.dto.policyDataDTO;

import javax.validation.constraints.NotBlank;
import org.tat.fni.api.common.emumdata.ProductType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PolicyDataDTO {
	
	@ApiModelProperty(position = 0, example = "FNI-HO/SL/PO/00000001/6-2019", required = true)
	@NotBlank(message = "ProposalNo is mandatory")
	private String proposalNo;
	
	@ApiModelProperty(position = 1, example = "LIFE", required = true)
	@NotBlank(message = "Product type is mandatory")
	private ProductType productType;

}
