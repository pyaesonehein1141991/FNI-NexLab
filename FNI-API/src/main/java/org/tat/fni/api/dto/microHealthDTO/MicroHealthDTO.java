package org.tat.fni.api.dto.microHealthDTO;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.tat.fni.api.common.emumdata.SaleChannelType;
import org.tat.fni.api.configuration.DateHandler;
import org.tat.fni.api.domain.HealthType;
import org.tat.fni.api.dto.customerDTO.CustomerDto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MicroHealthDTO {

	@ApiModelProperty(position = 0, example = "10", required = true)
	@NotNull(message = "Period Month is mandatory")
	private int periodMonth;

	@ApiModelProperty(position = 1, example = "AGENT", required = true)
	@NotNull(message = "SaleChannel Type is mandatory")
	private SaleChannelType saleChannelType;

	@ApiModelProperty(position = 2, example = "MICROHEALTH", required = true)
	@NotNull(message = "Health Type is mandatory")
	private HealthType healthType;
	
	@Valid
	@ApiModelProperty(position = 3, required = true)
	@NotNull(message = "customer is mandatory")
	private CustomerDto customer;

	@ApiModelProperty(position = 5, example = "ISSYS002001000000000103062019", required = true)
	@NotBlank(message = "Agent ID is mandatory")
	private String agentId;

	@ApiModelProperty(position = 9, example = "ISSYS0090001000000000429032013", required = true)
	@NotBlank(message = "Payment Type ID is mandatory")
	private String paymentTypeId;

	@ApiModelProperty(position = 10, example = "2020-12-16", required = true)
	@NotNull(message = "SubmittedDate is mandatory")
	@JsonDeserialize(using = DateHandler.class)
	private Date submittedDate;

	@ApiModelProperty(position = 11, example = "2020-12-18", required = true)
	@NotNull(message = "StartDate is mandatory")
	@JsonDeserialize(using = DateHandler.class)
	private Date startDate;

	@ApiModelProperty(position = 12, example = "2021-12-18", required = true)
	@NotNull(message = "EndDate is mandatory")
	@JsonDeserialize(using = DateHandler.class)
	private Date endDate;
	
//	@ApiModelProperty(position = 14, example = "false", required = true)
//	@NotNull(message = "status is mandatory")
//	private boolean status;


	@ApiModelProperty(position = 15, required = true)
	@NotNull(message = "proposalInsuredPersonList is mandatory")
	private List<MicroHealthProposalInsuredPersonDTO> microhealthproposalInsuredPersonList;

}
