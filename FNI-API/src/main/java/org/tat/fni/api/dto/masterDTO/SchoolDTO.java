package org.tat.fni.api.dto.masterDTO;

import org.tat.fni.api.common.emumdata.SchoolLevelType;
import org.tat.fni.api.common.emumdata.SchoolType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SchoolDTO {
	
	private String id;

	private String name;

	private SchoolType schoolType;

	private SchoolLevelType schoolLevelType;

	private String phoneNo;

	private String schoolCodeNo;

}
