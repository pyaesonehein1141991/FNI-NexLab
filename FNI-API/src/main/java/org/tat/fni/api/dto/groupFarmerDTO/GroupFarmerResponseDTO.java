package org.tat.fni.api.dto.groupFarmerDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupFarmerResponseDTO {
	
	private String groupProposalNo;
	private String bpmsInsuredPersonId;
	private String proposalNo;
	private String policyNo;
	private String customerId;
	

}
