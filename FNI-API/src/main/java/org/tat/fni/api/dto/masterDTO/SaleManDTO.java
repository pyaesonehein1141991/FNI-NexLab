package org.tat.fni.api.dto.masterDTO;

import java.util.Date;

import org.tat.fni.api.common.emumdata.Gender;
import org.tat.fni.api.common.emumdata.IdType;
import org.tat.fni.api.common.emumdata.MaritalStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaleManDTO {
	
	private String id;

	private Date dateOfBirth;

	private String CodeNo;

	private String idNo;

	private IdType idType;

	private String initialId;

	private String fullName;

	private String address;

}
