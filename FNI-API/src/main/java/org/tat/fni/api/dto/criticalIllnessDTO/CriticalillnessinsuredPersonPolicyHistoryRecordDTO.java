package org.tat.fni.api.dto.criticalIllnessDTO;

import java.util.Date;
import javax.validation.constraints.NotNull;
import org.tat.fni.api.configuration.DateHandler;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CriticalillnessinsuredPersonPolicyHistoryRecordDTO {


  @ApiModelProperty(position = 1, example = "ISSYS035001000000000126112013  ", required = true)
  @NotNull(message = "COMPANYID is mandatory")
  private String COMPANYID;

  @ApiModelProperty(position = 2, example = "ISPRD003001000009575103082018  ", required = true)
  @NotNull(message = "PRODUCTID is mandatory")
  private String PRODUCTID;


  @ApiModelProperty(position = 3, example = "FNI-HO/SL/PL/00000005/6-2019", required = true)
  private String policyNo;


  @ApiModelProperty(position = 4, example = "2019-12-16", required = true)
  @NotNull(message = "fromDate is mandatory")
  @JsonDeserialize(using = DateHandler.class)
  private Date fromDate;

  @ApiModelProperty(position = 5, example = "2019-12-16", required = true)
  @NotNull(message = "toDate is mandatory")
  @JsonDeserialize(using = DateHandler.class)
  private Date toDate;


  @ApiModelProperty(position = 6, example = "5", required = true)
  @NotNull(message = "unit is mandatory")
  private int unit;



}
