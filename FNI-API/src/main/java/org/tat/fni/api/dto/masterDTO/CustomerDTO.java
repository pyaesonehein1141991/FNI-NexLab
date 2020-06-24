package org.tat.fni.api.dto.masterDTO;

import java.util.Date;

import org.tat.fni.api.common.emumdata.Gender;
import org.tat.fni.api.common.emumdata.IdType;
import org.tat.fni.api.common.emumdata.MaritalStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDTO {
	
	private String id;

	private Date dateOfBirth;

	private String fatherName;

	private String fullIdNo;

	private Gender gender;

	private IdType idType;

	private String initialId;

	private MaritalStatus marialStatus;

	private String mobile;

	private String firstName;

	private String middleName;

	private String lastName;

	private String address;

}
