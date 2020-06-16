package org.tat.fni.api.dto.responseDTO.policyResponse;

import java.util.List;

import org.tat.fni.api.dto.retrieveDTO.policyData.AgentData;
import org.tat.fni.api.dto.retrieveDTO.policyData.BeneficiaryData;
import org.tat.fni.api.dto.retrieveDTO.policyData.BillCollectionData;
import org.tat.fni.api.dto.retrieveDTO.policyData.CustomerData;
import org.tat.fni.api.dto.retrieveDTO.policyData.InsuredPersonData;
import org.tat.fni.api.dto.retrieveDTO.policyData.PolicyData;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDataDTO {

	private String proposalNo;

	private boolean isApprove;

	private PolicyData policyData;

	private CustomerData customer;

	private AgentData agent;

	private List<InsuredPersonData> insuredPersonDataList;

	private List<BeneficiaryData> beneficiaryDataList;

	private List<BillCollectionData> billCollectionDataList;

}
