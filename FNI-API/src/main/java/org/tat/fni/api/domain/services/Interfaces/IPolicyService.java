package org.tat.fni.api.domain.services.Interfaces;

import java.util.List;

import org.tat.fni.api.dto.retrieveDTO.policyData.PolicyDataCriteria;

public interface IPolicyService {
	
	public <T> List<T> getResponseData(PolicyDataCriteria policyDto);

}
