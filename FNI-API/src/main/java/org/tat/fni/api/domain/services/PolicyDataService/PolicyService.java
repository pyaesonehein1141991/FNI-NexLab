package org.tat.fni.api.domain.services.PolicyDataService;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.tat.fni.api.domain.services.Interfaces.IPolicyDataService;
import org.tat.fni.api.domain.services.Interfaces.IPolicyService;
import org.tat.fni.api.dto.responseDTO.policyResponse.ResponseDataLifeDTO;
import org.tat.fni.api.dto.responseDTO.policyResponse.ResponseDataMedicalDTO;
import org.tat.fni.api.dto.retrieveDTO.policyData.PolicyDataCriteria;

@Service
public class PolicyService implements IPolicyService {
	
	@Resource(name = "lifePolicyService")
	private IPolicyDataService lifePolicyDataService;
	
	@Resource(name = "medicalPolicyService")
	private IPolicyDataService medicalPolicyDataService;
	
	@Override
	public <T> List<T> getResponseData(PolicyDataCriteria policyDto) {
		
		List<ResponseDataLifeDTO> responseLifeDataList = new ArrayList<ResponseDataLifeDTO>();
		List<ResponseDataMedicalDTO> responseMedicalDataList = new ArrayList<ResponseDataMedicalDTO>();
		List<T> responseDataList = null;

		String proposalId = medicalPolicyDataService.getProposalIdFromRepo(policyDto.getProposalNo());
		
		proposalId = StringUtils.isEmpty(proposalId) 
				? lifePolicyDataService.getProposalIdFromRepo(policyDto.getProposalNo())
				: "";
		
		if(proposalId.contains("LIF")) {
			responseLifeDataList = lifePolicyDataService.getResponseData(policyDto);
			responseDataList = (List<T>) responseLifeDataList;
		}else {
			responseMedicalDataList = medicalPolicyDataService.getResponseData(policyDto);
			responseDataList = (List<T>) responseMedicalDataList;
		}
		
		return responseDataList;
	}

}
