package org.tat.fni.api.dto.groupFarmerDTO;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.tat.fni.api.configuration.DateHandler;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FarmerProposalDTO {

	@ApiModelProperty(position = 0, example = "2019-12-16", required = true)
	@NotNull(message = "SubmittedDate is mandatory")
	@JsonDeserialize(using = DateHandler.class)
	private Date submittedDate;
	
	@ApiModelProperty(position = 1, example = "2019-12-16", required = true)
	@NotNull(message = "Start Date is mandatory")
	@JsonDeserialize(using = DateHandler.class)
	private Date startDate;
	
	@ApiModelProperty(position = 2, example = "ISSYS001000018029701042019", required = true)
	@NotBlank(message = "Customer ID is mandatory")
	private String CustomerId;

	/*
	 * @ApiModelProperty(position = 1, example = "2019-12-16", required = true)
	 * 
	 * @NotNull(message = "End Date is mandatory")
	 * 
	 * @JsonDeserialize(using = DateHandler.class) private Date endDate;
	 * 
	 * @ApiModelProperty(position = 2, example = "1", required = true)
	 * 
	 * @NotNull(message = "No of Insuredperson is mandatory") private int
	 * noOfInsuredPerson;
	 * 
	 * @ApiModelProperty(position = 3, example = "100000", required = true)
	 * 
	 * @NotNull(message = "Total SI is mandatory") private double totalSI;
	 */

	@ApiModelProperty(position = 3, example = "ISSYS033000009784102042019", required = true)
	@NotNull(message = "organizationID SI is mandatory")
	private String organizationID;

	@ApiModelProperty(position = 4, example = "ISSYS0090001000000000429032013", required = true)
	@NotBlank(message = "paymentTypeId is mandatory")
	private String paymentTypeId;
	
	@ApiModelProperty(position = 5, example = "agent", required = true)
	@NotBlank(message = "saleChannel is mandatory")
	private String saleChannel;

	@ApiModelProperty(position = 6, example = "ISSYS002000009756217052019", required = true)
	@NotBlank(message = "agentID is mandatory")
	private String agentID;

	/*
	 * @ApiModelProperty(position = 7, example = "ISSYS001000005575112092016")
	 * private String referralID;
	 * 
	 * @ApiModelProperty(position = 8, example = "ISSYS022000009634116052019")
	 * private String saleManId;
	 */

	@ApiModelProperty(position = 7, example = "BANCH00000000000000129032018", required = true)
	@NotBlank(message = "branchId is mandatory")
	private String branchId;

	
//	@ApiModelProperty(position = 10, example = "11", required = true)
//	@NotBlank(message = "salePointId is mandatory") 
//	private String salePointId;
	
	@Valid
	@ApiModelProperty(position = 8, required = true)
	@NotNull(message = "proposalInsuredPersonList is mandatory") 
	private List<FarmerProposalInsuredPersonDTO> proposalInsuredPersonList;
	
	@Valid
	@ApiModelProperty(position = 9, required = true)
	@NotNull(message = "proposalInsuredPersonBeneficiariesList is mandatory") 
	private List<FarmerProposalInsuredPersonBeneficiariesDTO> proposalInsuredPersonBeneficiariesList;
	
//	@ApiModelProperty(position = 12, example = "TRF", required = true)
//	@NotBlank(message = "paymentChannel is mandatory") 
//	private String paymentChannel;
//	
//	@ApiModelProperty(position = 13, example = "ISSYS010005000000021118072016",
//	 required = true) 
//	private String toBank;
//	
//	@ApiModelProperty(position = 14, example = "ISSYS0100001000000000713032013",
//	 required = true) 
//	private String fromBank;
//	 
//	@ApiModelProperty(position = 15, example = "CH34345345", required = true)
//	private String chequeNo;
//	
//	@ApiModelProperty(position = 16, example = "1234", required = true)
//	@NotBlank(message = "userId is mandatory") 
//	private String userId;
//	
//	@ApiModelProperty(position = 17, example = "BPMS/CSH/001", required = true)
//	@NotBlank(message = "bpmsReceiptNo is mandatory") 
//	private String bpmsReceiptNo;
//	
//	@ApiModelProperty(position = 18, example = "BPMS/P/001", required = true)
//	@NotBlank(message = "bpmsProposalNo is mandatory") 
//	private String bpmsProposalNo;
	
}
