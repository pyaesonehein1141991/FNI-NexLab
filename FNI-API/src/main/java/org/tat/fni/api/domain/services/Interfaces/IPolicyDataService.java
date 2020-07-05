package org.tat.fni.api.domain.services.Interfaces;

import java.util.List;

import org.tat.fni.api.dto.retrieveDTO.policyData.AgentData;
import org.tat.fni.api.dto.retrieveDTO.policyData.BillCollectionData;
import org.tat.fni.api.dto.retrieveDTO.policyData.CustomerData;
import org.tat.fni.api.dto.retrieveDTO.policyData.PolicyData;
import org.tat.fni.api.dto.retrieveDTO.policyData.PolicyDataCriteria;

public interface IPolicyDataService {
	
	public <T> List<T> getResponseData(PolicyDataCriteria policyDto);
	
	public PolicyData getPolicyData(PolicyDataCriteria policyDto);
	
	public CustomerData getCustomerData(PolicyDataCriteria policyDto);
	
	public AgentData getAgentData(PolicyDataCriteria policyDto);
	
	public <T> List<T> getInsuredPersonData(PolicyDataCriteria policyDto);
	
	public <T> List<T> getBeneficiaryData(PolicyDataCriteria policyDto);
	
	public List<BillCollectionData> getBillCollectionData(PolicyDataCriteria policyDto);
	
	public String getProposalIdFromRepo(String proposalNumber);

}
