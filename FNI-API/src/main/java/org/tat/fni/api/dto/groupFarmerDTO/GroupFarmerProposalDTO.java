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
public class GroupFarmerProposalDTO {


  @ApiModelProperty(position = 0, example = "2019-12-16", required = true)
  @NotNull(message = "SubmittedDate is mandatory")
  @JsonDeserialize(using = DateHandler.class)
  private Date submittedDate;

  @ApiModelProperty(position = 1, example = "2019-12-16", required = true)
  @NotNull(message = "End Date is mandatory")
  @JsonDeserialize(using = DateHandler.class)
  private Date endDate;

  @ApiModelProperty(position = 2, example = "1", required = true)
  @NotNull(message = "No of Insuredperson is mandatory")
  private int noOfInsuredPerson;


  @ApiModelProperty(position = 3, example = "1", required = true)
  @NotNull(message = "Total SI is mandatory")
  private double totalSI;

  @ApiModelProperty(position = 4, example = "BANCH00000000000000129032018", required = true)
  @NotBlank(message = "Branch is mandatory")
  private String branchId;

  @ApiModelProperty(position = 5, example = "ISSYS002000009755110052019", required = true)
  @NotBlank(message = "agentID is mandatory")
  private String agentId;

  @ApiModelProperty(position = 6, example = "ISSYS022000009634116052019")
  private String saleManId;

  @ApiModelProperty(position = 7, example = "ISSYS001000005575112092016")
  private String referralId;

  @ApiModelProperty(position = 8, example = "ISSYS033000009784102042019")
  @NotBlank(message = "Organization Id is mandatory")
  private String organizationId;


  @ApiModelProperty(position = 10, example = "11", required = true)
  @NotBlank(message = "salePointId is mandatory")
  private String salePointId;

  @Valid
  @ApiModelProperty(position = 11, required = true)
  @NotNull(message = "FarmerProposal is mandatory")
  private List<FarmerProposalDTO> farmerProposalDTO;



}
