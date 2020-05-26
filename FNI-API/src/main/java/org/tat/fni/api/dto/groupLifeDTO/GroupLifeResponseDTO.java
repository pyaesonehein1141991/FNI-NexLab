package org.tat.fni.api.dto.groupLifeDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupLifeResponseDTO {
	
	private String proposalID;
	private String proposalNo;
	private double proposedPremium;

}
