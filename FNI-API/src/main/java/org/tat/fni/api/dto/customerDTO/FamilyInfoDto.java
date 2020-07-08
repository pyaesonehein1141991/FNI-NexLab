package org.tat.fni.api.dto.customerDTO;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.tat.fni.api.common.emumdata.IdType;
import org.tat.fni.api.configuration.DateHandler;
import org.tat.fni.api.dto.retrieveDTO.NameDto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FamilyInfoDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(position = 0, required = false)
	private String initialId;

	@ApiModelProperty(position = 1, required = false)
	private String idNo;

	@ApiModelProperty(position = 2,example="2020-12-16", required = false)
	@JsonDeserialize(using = DateHandler.class)
	private Date dateOfBirth;

	@ApiModelProperty(position = 3, required = false)
	private String refCustomerId;

	@ApiModelProperty(position = 4, required = true)
	@NotBlank(message = "IdType is mandatory")
	private NameDto name;

	@ApiModelProperty(position = 5,example = "STILL_APPLYING", required = true)
	@NotBlank(message = "IdType is mandatory")
	private IdType idType;

	@ApiModelProperty(position = 6, example="ISSYS0120001000000000129032013", required = false)
	private String relationShipId;

	@ApiModelProperty(position = 7, example="ISSYS020001000000000107062019", required = false)
	private String industryId;

	@ApiModelProperty(position = 8, example="ISSYS011000009823001042019", required = true)
	@NotBlank(message = "occupationId is mandatory")
	private String occupationId;

	public FamilyInfoDto(String initialId, String idNo, IdType idType, Date dateOfBirth, String refCustomerId,
			NameDto name, String relationShipId, String industryId, String occupationId) {
		this.initialId = initialId;
		this.idNo = idNo;
		this.idType = idType;
		this.dateOfBirth = dateOfBirth;
		this.refCustomerId = refCustomerId;
		this.name = name;
		this.idType = idType;
		this.relationShipId = relationShipId;
		this.industryId = industryId;
		this.occupationId = occupationId;
	}

}