package org.tat.fni.api.dto.proposalResponseDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProposalResponseDTO {

	private String proposalID;
	private String proposalNo;
	private double proposedPremium;

}