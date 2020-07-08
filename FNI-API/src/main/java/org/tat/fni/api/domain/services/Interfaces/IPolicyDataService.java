package org.tat.fni.api.domain.services.Interfaces;

import org.tat.fni.api.dto.retrieveDTO.policyData.BillCollectionData;
import org.tat.fni.api.dto.retrieveDTO.policyData.PolicyData;

public interface IPolicyDataService {
	
	public <T> T getResponseData(String proposalNo);

	public PolicyData getPolicyData(String proposalNo);

	public BillCollectionData getBillCollectionData(String proposalNo);

	public String getProposalIdFromRepo(String proposalNo);

}
