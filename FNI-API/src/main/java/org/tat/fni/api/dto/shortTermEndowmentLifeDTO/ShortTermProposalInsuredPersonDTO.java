package org.tat.fni.api.dto.shortTermEndowmentLifeDTO;

import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.tat.fni.api.configuration.DateHandler;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ShortTermProposalInsuredPersonDTO {

  @ApiModelProperty(position = 0, example = "U", required = true)
  @NotBlank(message = "InitialId is mandatory")
  private String initialId;

  @ApiModelProperty(position = 1, example = "23", required = true)
  @NotNull(message = "age is mandatory")
  private int age;

  @ApiModelProperty(position = 3, example = "1000000", required = true)
  @NotNull(message = "proposedSumInsured is mandatory")
  private double proposedSumInsured;

  @ApiModelProperty(position = 4, example = "1000000", required = true)
  @NotNull(message = "proposedPremium is mandatory")
  private double proposedPremium;

  @ApiModelProperty(position = 7, example = "1999-12-16", required = true)
  @NotNull(message = "dateOfBirth is mandatory")
  @JsonDeserialize(using = DateHandler.class)
  private Date dateOfBirth;

  @ApiModelProperty(position = 8, example = "U Maung Maung", required = true)
  @NotBlank(message = "fatherName is mandatory")
  private String fatherName;

  @ApiModelProperty(position = 9, example = "MALE", required = true)
  @NotNull(message = "gender is mandatory")
  private String gender;

  @ApiModelProperty(position = 10, example = "123123123")
  private String idNo;

  @ApiModelProperty(position = 11, example = "NRCNO", required = true)
  @NotNull(message = "idType is mandatory")
  private String idType;

  @ApiModelProperty(position = 12, example = "AUNG", required = true)
  @NotNull(message = "firstName is mandatory")
  private String firstName;

  @ApiModelProperty(position = 13, example = "AUNG")
  private String middleName;

  @ApiModelProperty(position = 14, example = "AUNG")
  private String lastName;

  @ApiModelProperty(position = 15, example = "Yangon", required = true)
  @NotNull(message = "residentAddress is mandatory")
  private String residentAddress;

  @ApiModelProperty(position = 16, example = "ISSYS004001000000731326012017", required = true)
  @NotBlank(message = "townshipId is mandatory")
  @NotEmpty
  private String townshipId;

  @ApiModelProperty(position = 17, example = "ISSYS011000009823001042019", required = true)
  private String occupationID;

  @Valid
  @ApiModelProperty(position = 19)
  @NotNull(message = "insuredPersonBeneficiariesList is mandatory")
  private List<ShortTermProposalInsuredPersonBeneficiariesDTO> insuredPersonBeneficiariesList;

}
