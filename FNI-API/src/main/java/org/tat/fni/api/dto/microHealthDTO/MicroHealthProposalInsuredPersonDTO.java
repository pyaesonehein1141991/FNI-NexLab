package org.tat.fni.api.dto.microHealthDTO;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.tat.fni.api.dto.InsuredPersonAddOnDTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MicroHealthProposalInsuredPersonDTO {

	@ApiModelProperty(position = 0, example = "23", required = true)
	@NotNull(message = "age is mandatory")
	private int age;

	@ApiModelProperty(position = 1, example = "true", required = true)
	@NotNull(message = "approve is mandatory")
	private boolean approve;

	@ApiModelProperty(position = 2, example = "100000", required = true)
	@NotNull(message = "premium is mandatory")
	private double premium;

	@ApiModelProperty(position = 3, example = "true", required = true)
	@NotNull(message = "approve is mandatory")
	private boolean needMedicalCheckup;

	@ApiModelProperty(position = 4, example = "5", required = true)
	@NotNull(message = "unit is mandatory")
	private int unit;

	@ApiModelProperty(position = 5, example = "ISSYS0120001000000000129032013", required = true)
	@NotBlank(message = "relationshipId is mandatory")
	@NotEmpty
	private String relationshipId;

	@ApiModelProperty(position = 6, example = "some reason")
	private String rejectReason;

	@ApiModelProperty(position = 7, example = "ISMED023001000000000110062019")
	private String guardianId;

	@Valid
	@ApiModelProperty(position = 8)
	@NotNull(message = "insuredPersonBeneficiariesList is mandatory")
	private List<MicroHealthProposalInsuredPersonBeneficiariesDTO> insuredPersonBeneficiariesList;

}
