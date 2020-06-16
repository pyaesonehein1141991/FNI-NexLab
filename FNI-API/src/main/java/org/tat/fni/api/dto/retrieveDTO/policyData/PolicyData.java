package org.tat.fni.api.dto.retrieveDTO.policyData;

import java.util.Date;

import org.tat.fni.api.common.emumdata.SaleChannelType;
import org.tat.fni.api.domain.PaymentType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PolicyData {

	private Date policyStartDate;

	private Date policyEndDate;

	private int periodMonth;

	private Date commenmanceDate;

	private String policyNo;

	private SaleChannelType saleChannelType;

	private String paymentType;

	private String salesPoints;

	private Date coverageDate;

	private String productName;

}
