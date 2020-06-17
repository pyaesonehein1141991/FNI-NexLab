package org.tat.fni.api.dto.responseDTO.policyResponse;

import java.util.List;

import org.tat.fni.api.dto.retrieveDTO.policyData.AgentData;
import org.tat.fni.api.dto.retrieveDTO.policyData.BeneficiaryMedicalData;
import org.tat.fni.api.dto.retrieveDTO.policyData.BillCollectionData;
import org.tat.fni.api.dto.retrieveDTO.policyData.CustomerData;
import org.tat.fni.api.dto.retrieveDTO.policyData.InsuredPersonMedicalData;
import org.tat.fni.api.dto.retrieveDTO.policyData.PolicyData;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDataMedicalDTO {

	private String proposalNo;

	private boolean isApprove;

	private PolicyData policyData;

	private CustomerData customer;

	private AgentData agent;

	private List<InsuredPersonMedicalData> insuredPersonDataList;

	private List<BeneficiaryMedicalData> beneficiaryDataList;

	private List<BillCollectionData> billCollectionDataList;

}
