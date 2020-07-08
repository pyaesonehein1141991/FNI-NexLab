package org.tat.fni.api.dto.policyDataDTO;

import java.util.List;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class PolicyDataCriteria {
	
	public List<PolicyDataDTO> policyDataList;

}
