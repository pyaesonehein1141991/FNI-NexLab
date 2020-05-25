package org.tat.fni.api.dto.healthInsuranceDTO;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class IndividualHealthProposalInsuredPersonDTO {

  @ApiModelProperty(position = 0, example = "U", required = true)
  @NotBlank(message = "InitialId is mandatory")
  private String initialId;

  @ApiModelProperty(position = 1, example = "23", required = true)
  @NotNull(message = "age is mandatory")
  private int age;


  @ApiModelProperty(position = 2, example = "true", required = true)
  @NotNull(message = "approve is mandatory")
  private boolean approve;


  @ApiModelProperty(position = 4, example = "100000", required = true)
  @NotNull(message = "premium is mandatory")
  private double premium;

  @ApiModelProperty(position = 5, example = "true", required = true)
  @NotNull(message = "approve is mandatory")
  private boolean needMedicalCheckup;

  @ApiModelProperty(position = 6, example = "5", required = true)
  @NotNull(message = "unit is mandatory")
  private int unit;



  @ApiModelProperty(position = 7, example = "ISSYS0120001000000000129032013", required = true)
  @NotBlank(message = "relationshipId is mandatory")
  @NotEmpty
  private String relationshipId;



  @ApiModelProperty(position = 18, example = "ISSYS001001000000000103062019")
  private String customerID;



  @Valid
  @ApiModelProperty(position = 20)
  @NotNull(message = "insuredPersonBeneficiariesList is mandatory")
  private List<IndividualHealthProposalInsuredPersonBeneficiariesDTO> insuredPersonBeneficiariesList;


  @Valid
  @ApiModelProperty(position = 21)
  @NotNull(message = "insuredPersonBeneficiariesList is mandatory")
  private List<IndividualHealthinsuredPersonPolicyHistoryRecordDTO> insuredPersonPolicyHistoryRecordList;



}
