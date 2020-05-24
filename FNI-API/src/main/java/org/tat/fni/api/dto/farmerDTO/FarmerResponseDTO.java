package org.tat.fni.api.dto.farmerDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FarmerResponseDTO {
	
	private String proposalID;
	private String proposalNo;
	private double proposedPremium;

}
