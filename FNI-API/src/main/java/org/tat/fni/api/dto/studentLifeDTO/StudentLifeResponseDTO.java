package org.tat.fni.api.dto.studentLifeDTO;

import org.tat.fni.api.dto.snakeBiteDTO.SnakeBiteResponseDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentLifeResponseDTO {
	
	private String proposalID;
	private String proposalNo;
	private double proposedPremium;

}
