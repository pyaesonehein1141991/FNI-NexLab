package org.tat.fni.api.dto.masterDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrganizationDTO {
	
	private String id;

	private String name;

	private String phone;

	private String ownerName;

	private String address;

}
