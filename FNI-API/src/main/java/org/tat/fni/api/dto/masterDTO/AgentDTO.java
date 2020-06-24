package org.tat.fni.api.dto.masterDTO;

import java.util.Date;

import org.tat.fni.api.common.emumdata.Gender;
import org.tat.fni.api.common.emumdata.IdType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgentDTO {
	
	private String id;

	private String name;

	private String codeNo;

	private String fatherName;

	private Gender gender;

	private IdType idType;

	private String initialId;
	
	private Date joinDate;
	
	private String email;

}
