package org.tat.fni.api.dto.policyDataDTO;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.tat.fni.api.dto.farmerDTO.FarmerPolicyDataDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PolicyDataCriteria {
	
	public List<PolicyDataDTO> policyDataList;

}
