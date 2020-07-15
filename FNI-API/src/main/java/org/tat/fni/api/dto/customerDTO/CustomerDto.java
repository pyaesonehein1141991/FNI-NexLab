package org.tat.fni.api.dto.customerDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.tat.fni.api.common.emumdata.Gender;
import org.tat.fni.api.common.emumdata.IdType;
import org.tat.fni.api.common.emumdata.MaritalStatus;
import org.tat.fni.api.configuration.DateHandler;
import org.tat.fni.api.dto.retrieveDTO.NameDto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(position = 0, required = false)
	private String initialId;

	@ApiModelProperty(position = 1, required = false)
	private String fatherName;

	@ApiModelProperty(position = 2, example="009354", required = false)
	private String idNo;

	@ApiModelProperty(position = 3, example = "2020-12-16", required = true)
	@NotNull(message = "DateOfBirth is mandatory")
	@JsonDeserialize(using = DateHandler.class)
	private Date dateOfBirth;

	@ApiModelProperty(position = 4, required = false)
	private String labourNo;

	@ApiModelProperty(position = 5,example = "MALE", required = true)
	@NotNull(message = "Gender is mandatory")
	private Gender gender;

	@ApiModelProperty(position = 6,example = "NRCNO", required = true)
	@NotNull(message = "Id Type is mandatory")
	private IdType idType;

	@ApiModelProperty(position = 7, example = "SINGLE", required = true)
	@NotNull(message = "Marital status is mandatory")
	private MaritalStatus maritalStatus;

	@Valid
	@ApiModelProperty(position = 8, required = false)
	private OfficeAddressDto officeAddress;

	@Valid
	@ApiModelProperty(position = 9, required = false)
	private PermanentAddressDto permanentAddress;

	@Valid
	@ApiModelProperty(position = 10, required = true)
	@NotNull(message = "ResidentAddress is mandatory")
	private ResidentAddressDto residentAddress;

	@Valid
	@ApiModelProperty(position = 11, required = true)
	private ContentInfoDto contentInfo;

	@Valid
	@ApiModelProperty(position = 12, required = true)
	@NotNull(message = "Name is mandatory")
	private NameDto name;

	@ApiModelProperty(position = 13, required = false)
	private List<FamilyInfoDto> familyInfoList;

	@ApiModelProperty(position = 14, example="ISSYS016001000009576109032019", required = false)
	private String qualificationId;

	@ApiModelProperty(position = 15, example="ISSYS017001000000224213112015", required = false)
	private String religionId;

	@ApiModelProperty(position = 16, example="ISSYS011000009823001042019", required = false)
	private String occupationId;

	@ApiModelProperty(position = 17, example="ISSYS0070001000000000129032013", required = true)
	@NotBlank(message = "CountryId ID is mandatory")
	private String countryId;

}
