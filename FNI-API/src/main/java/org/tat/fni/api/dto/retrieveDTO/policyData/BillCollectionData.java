package org.tat.fni.api.dto.retrieveDTO.policyData;

import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BillCollectionData {
	
	private int lastPaymentTerm;

	private Date coverDate;

	private int totalPaymentTerm;

	private List<RemainingDate> remainingDateList;
	
}
