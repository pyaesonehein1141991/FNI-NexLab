package org.tat.fni.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TownshipDTO {
	
	private String id;

	private String name;

	private String code;

}
