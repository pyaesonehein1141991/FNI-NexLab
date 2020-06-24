package org.tat.fni.api.dto.masterDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProvinceDTO {
	
	private String id;

	private String name;

	private String description;

	private String code;

}
