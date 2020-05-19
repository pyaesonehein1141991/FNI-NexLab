package org.tat.fni.api.dto.billcollectionDTO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BillCollectionDTO {


  @ApiModelProperty(position = 1, example = "S/1909/0000000053", required = true)
  @NotBlank(message = "Policy No is mandatory")
  private String policyNo;

  @ApiModelProperty(position = 2, example = "1", required = true)
  @NotNull(message = "payment Times  is mandatory")
  @Min(value = 1,
      message = "Payment Times  must be greater than or equal to 1,must be greater than 0")
  private int paymentTimes;

  @ApiModelProperty(position = 3, example = "30000", required = true)
  @NotNull(message = "Amount is mandatory")
  @Positive
  @Min(value = 1, message = "Amount must be greater than or equal to 1,must be greater than 0")
  private double amount;

  @ApiModelProperty(position = 4, example = "CSH", required = true)
  @NotBlank(message = "payment channel is mandatory")
  private String paymentChannel;

  @ApiModelProperty(position = 5, example = "1", required = true)
  @NotBlank(message = "SalePointId is mandatory")
  private String salePointId;

  @ApiModelProperty(position = 6, example = "ISSYS010000009558003042019")
  private String fromBank;


  @ApiModelProperty(position = 7, example = "ISSYS010000009558103042019")
  private String toBank;

  @ApiModelProperty(position = 8, example = "9558103042019")
  private String chequeNo;


  @ApiModelProperty(position = 9, example = "36221e42-a0000040-133dc830-59dccb1a", required = true)
  private String bpmNo;

  @ApiModelProperty(position = 10, example = "dddd")
  private String paymentBranch;


}
