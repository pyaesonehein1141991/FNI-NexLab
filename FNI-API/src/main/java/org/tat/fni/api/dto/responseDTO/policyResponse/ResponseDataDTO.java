package org.tat.fni.api.dto.responseDTO.policyResponse;

import org.tat.fni.api.dto.retrieveDTO.policyData.BillCollectionData;
import org.tat.fni.api.dto.retrieveDTO.policyData.PolicyData;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDataDTO {
	
	private String proposalNo;

	private boolean isApprove;

	private PolicyData policyData;

	private BillCollectionData billCollectionData;

}
