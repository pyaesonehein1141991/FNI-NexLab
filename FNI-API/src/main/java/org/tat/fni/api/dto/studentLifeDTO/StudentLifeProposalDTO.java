package org.tat.fni.api.dto.studentLifeDTO;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.tat.fni.api.configuration.DateHandler;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StudentLifeProposalDTO {

  @ApiModelProperty(position = 0, example = "2019-12-16", required = true)
  @NotNull(message = "SubmittedDate is mandatory")
  @JsonDeserialize(using = DateHandler.class)
  private Date submittedDate;

  @ApiModelProperty(position = 1, example = "U ", required = true)
  @NotBlank(message = "Salutation is mandatory")
  private String salutation;

  @ApiModelProperty(position = 2, example = "Mg", required = true)
  @NotBlank(message = "FirstName is mandatory")
  private String firstName;

  @ApiModelProperty(position = 3, example = "Mg ")
  private String middleName;

  @ApiModelProperty(position = 4, example = "Mg ")
  private String lastName;

  @ApiModelProperty(position = 5, example = "2019-12-16", required = true)
  @NotNull(message = "DateOfBirth is mandatory")
  @JsonDeserialize(using = DateHandler.class)
  private Date dateOfBirth;

  @ApiModelProperty(position = 6, example = "Aung", required = true)
  @NotBlank(message = "FatherName is mandatory")
  private String fatherName;

  @ApiModelProperty(position = 7, example = "098888888", required = true)
  @NotBlank(message = "Phone is mandatory")
  private String phone;

  @ApiModelProperty(position = 8, example = "www.abc@mail.com")
  private String email;

  @ApiModelProperty(position = 9, example = "NRCNO", required = true)
  @NotBlank(message = "idType is mandatory")
  private String idType;

  @ApiModelProperty(position = 10, example = "9/MAMANA(N)456789")
  private String idNo;

  @ApiModelProperty(position = 11, example = "ISSYS011000009823001042019", required = true)
  @NotBlank(message = "occupationId is mandatory")
  private String occupationId;

  @ApiModelProperty(position = 12, example = "MARRIED", required = true)
  @NotBlank(message = "martialStatus is mandatory")
  private String martialStatus;

  @ApiModelProperty(position = 13, example = "FEMALE", required = true)
  @NotBlank(message = "Gender is mandatory")
  private String gender;

  @ApiModelProperty(position = 14, example = "ISSYS012000009552804092019", required = true)
  @NotBlank(message = "relationshipId is mandatory")
  private String relationshipId;

  @ApiModelProperty(position = 15, example = "ISSYS007001000000251219122015", required = true)
  @NotBlank(message = "nationalityId is mandatory")
  private String nationalityId;

  @ApiModelProperty(position = 16, example = "ISSYS004000009724620062019", required = true)
  @NotBlank(message = "residentTownshipId is mandatory")
  private String residentTownshipId;

  @ApiModelProperty(position = 17, example = "Yangon", required = true)
  @NotBlank(message = "residentAddress is mandatory")
  private String residentAddress;

  @ApiModelProperty(position = 18, example = "ISSYS004000009724620062019")
  private String officeTownshipId;

  @ApiModelProperty(position = 19, example = "Yangon", required = true)
  private String officeAddress;

  @ApiModelProperty(position = 3, example = "BANCH00000000000000129032018", required = true)
  @NotBlank(message = "branchId is mandatory")
  private String branchId;

  @ApiModelProperty(position = 5, example = "ISSYS001000005575112092016")
  private String referralID;

  @ApiModelProperty(position = 6, example = "ISSYS001000018029701042019")
  private String customerID;

  @ApiModelProperty(position = 7, example = "ISSYS0090001000000000429032013", required = true)
  @NotBlank(message = "paymentTypeId is mandatory")
  private String paymentTypeId;

  @ApiModelProperty(position = 8, example = "ISSYS002000009756217052019", required = true)
  @NotBlank(message = "agentID is mandatory")
  private String agentID;

  @ApiModelProperty(position = 9, example = "ISSYS022000009634116052019")
  private String saleManId;

  @ApiModelProperty(position = 10, required = true)
  @NotNull(message = "proposalInsuredPersonList is mandatory")
  private List<StudentLifeProposalInsuredPersonDTO> proposalInsuredPersonList;

  @ApiModelProperty(position = 11, example = "11", required = true)
  @NotBlank(message = "salePointId is mandatory")
  private String salePointId;

  @ApiModelProperty(position = 12, example = "TRF", required = true)
  private String paymentChannel;

  @ApiModelProperty(position = 13, example = "ISSYS010005000000021118072016", required = true)
  private String toBank;

  @ApiModelProperty(position = 14, example = "ISSYS0100001000000000713032013", required = true)
  private String fromBank;

  @ApiModelProperty(position = 15, example = "CH34345345", required = true)
  private String chequeNo;

  @ApiModelProperty(position = 12, example = "1234", required = true)
  @NotBlank(message = "userId is mandatory")
  private String userId;

  // @ApiModelProperty(position = 13, example = "1", required = true)
  // @NotBlank(message = "EntityId is mandatory")
  // private String entityId;

//  @ApiModelProperty(position = 17, example = "36221e42-a0000040-133dc830-59dccb1a", required = true)
//  private String bpmNo;
  
//  @ApiModelProperty(position = 18, example = "BANCH00000000000000129032018", required = true)
//  @NotBlank(message = "payment BranchId is mandatory")
//  private String paymentBranchId;
  
  @ApiModelProperty(position = 20, example = "BPMS/P/0001", required = true)
  private String bpmsProposalNo;
  
  @ApiModelProperty(position = 21, example = "BPMS/CSH/0001", required = true)
  private String bpmsReceiptNo;
}
