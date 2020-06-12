package org.tat.fni.api.dto.responseDTO;

import java.util.Date;

import org.tat.fni.api.common.emumdata.ProposalStatus;
import org.tat.fni.api.domain.PolicyStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PolicyInformationResponseDTO {
	
	private ProposalStatus proposalStatus;
	private PolicyStatus policyStatus;
	private double premium;
	private Date startDate;
	private Date endDate;
	private int paymentTimes;
	private double oneYearPremium;
	private double termPremium;
	private double totalTerm;

}
