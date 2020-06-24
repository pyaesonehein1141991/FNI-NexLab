package org.tat.fni.api.dto.masterDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HospitalDTO {
	
	private String id;

	private String name;

	private String phone;

	private String address;

}
