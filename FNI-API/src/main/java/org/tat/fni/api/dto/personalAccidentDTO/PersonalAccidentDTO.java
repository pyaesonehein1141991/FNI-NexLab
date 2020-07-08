package org.tat.fni.api.dto.personalAccidentDTO;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.tat.fni.api.configuration.DateHandler;
import org.tat.fni.api.dto.customerDTO.CustomerDto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PersonalAccidentDTO {

	@ApiModelProperty(position = 0, example = "12", required = true)
	@NotNull(message = "Period Month is mandatory")
	private int periodMonth;

	@ApiModelProperty(position = 1, example = "Agent", required = true)
	@NotBlank(message = "SaleChannel Type is mandatory")
	private String saleChannelType;

	@ApiModelProperty(position = 2, example = "ISSYS002001000000000103062019", required = true)
	@NotBlank(message = "Agent ID is mandatory")
	private String agentId;

	@ApiModelProperty(position = 3, example = "BANCH00000000000000129032013", required = true)
	@NotBlank(message = "Branch ID is mandatory")
	private String branchId;
	
	@Valid
	@ApiModelProperty(position = 4, required = true)
	@NotNull(message = "customer is mandatory")
	private CustomerDto customer;

//	@ApiModelProperty(position = 4, example = "ISSYS001001000000000103062019", required = true)
//	@NotBlank(message = "Customer ID is mandatory")
//	private String customerId;

	@ApiModelProperty(position = 5, example = "ISSYS033001000000000104062019", required = true)
	@NotBlank(message = "Organization ID is mandatory")
	private String organizationId;

	@ApiModelProperty(position = 6, example = "ISPRD0030001000000002331032013", required = true)
	@NotBlank(message = "Product ID is mandatory")
	private String productId;

	@ApiModelProperty(position = 7, example = "ISSYS0090001000000000229032013", required = true)
	@NotBlank(message = "Payment Type ID is mandatory")
	private String paymentTypeId;

	@ApiModelProperty(position = 8, example = "2020-12-16", required = true)
	@NotNull(message = "SubmittedDate is mandatory")
	@JsonDeserialize(using = DateHandler.class)
	private Date submittedDate;

	@ApiModelProperty(position = 9, example = "2020-12-18", required = true)
	@NotNull(message = "StartDate is mandatory")
	@JsonDeserialize(using = DateHandler.class)
	private Date startDate;

	@ApiModelProperty(position = 10, example = "2021-12-18", required = true)
	@NotNull(message = "EndDate is mandatory")
	@JsonDeserialize(using = DateHandler.class)
	private Date endDate;

	@ApiModelProperty(position = 11, example = "ISSYS052001000000000101062019", required = true)
	@NotBlank(message = "Sales Points ID is mandatory")
	private String salesPointsId;
	
	@ApiModelProperty(position = 12, example = "false", required = true)
	@NotNull(message = "status is mandatory")
	private boolean status;


	@ApiModelProperty(position = 13, required = true)
	@NotNull(message = "proposalInsuredPersonList is mandatory")
	private List<PersonalAccidentProposalInsuredPersonDTO> proposalInsuredPersonList;

}
