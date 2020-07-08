package org.tat.fni.api.dto.retrieveDTO.policyData;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RemainingDate {

	private String date;

	private double agentCommission;

	private double termPremium;

	private boolean paid;

}
