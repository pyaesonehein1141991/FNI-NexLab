package org.tat.fni.api.domain.services.Interfaces;

import java.util.List;

import org.tat.fni.api.dto.policyDataDTO.PolicyDataCriteria;
import org.tat.fni.api.dto.retrieveDTO.policyData.AgentData;
import org.tat.fni.api.dto.retrieveDTO.policyData.BillCollectionData;
import org.tat.fni.api.dto.retrieveDTO.policyData.CustomerData;
import org.tat.fni.api.dto.retrieveDTO.policyData.PolicyData;

public interface IPolicyDataService {
	
	public <T> T getResponseData(String proposalNo);
	
	public PolicyData getPolicyData(String proposalNo);
	
	public CustomerData getCustomerData(String proposalNo);
	
	public AgentData getAgentData(String proposalNo);
	
	public <T> List<T> getInsuredPersonData(String proposalNo);
	
	public <T> List<T> getBeneficiaryData(String proposalNo);
	
	public List<BillCollectionData> getBillCollectionData(String proposalNo);
	
	public String getProposalIdFromRepo(String proposalNo);

}
