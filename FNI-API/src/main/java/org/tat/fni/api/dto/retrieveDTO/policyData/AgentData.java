package org.tat.fni.api.dto.retrieveDTO.policyData;

import org.tat.fni.api.common.emumdata.Gender;
import org.tat.fni.api.dto.retrieveDTO.NameDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgentData {

	private NameDto name;

	private String codeNo;

	private String fatherName;

	private Gender gender;

	private String idNo;

	private String email;

}
