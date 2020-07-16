package org.tat.fni.api.domain.services.PolicyDataService;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.tat.fni.api.common.emumdata.ProductType;
import org.tat.fni.api.domain.services.Interfaces.IPolicyDataService;
import org.tat.fni.api.domain.services.Interfaces.IPolicyService;
import org.tat.fni.api.dto.policyDataDTO.PolicyDataCriteria;
import org.tat.fni.api.dto.responseDTO.policyResponse.ResponseDataDTO;

@Service
public class PolicyService implements IPolicyService {
	
	@Resource(name = "lifePolicyService")
	private IPolicyDataService lifePolicyDataService;
	
	@Resource(name = "medicalPolicyService")
	private IPolicyDataService medicalPolicyDataService;
	
	ResponseDataDTO responseLifeData;
	ResponseDataDTO responseMedicalData;
	
	
	@Override
	public <T> List<T> getResponseData(PolicyDataCriteria policyDto) {
		
		List<T> responseDataList = new ArrayList<T>();
		
		policyDto.getPolicyDataList().forEach(policyData -> {
			if(policyData.getProductType().equals(ProductType.LIFE)) {
				responseLifeData = lifePolicyDataService.getResponseData(policyData.getProposalNo());
				responseDataList.add((T) responseLifeData);
			}else if(policyData.getProductType().equals(ProductType.MEDICAL)) {
				responseMedicalData = medicalPolicyDataService.getResponseData(policyData.getProposalNo());
				responseDataList.add((T) responseMedicalData);
			}
		});
		
		return responseDataList;
	}

}
