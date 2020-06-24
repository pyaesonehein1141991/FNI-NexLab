package org.tat.fni.api.dto.masterDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BranchDTO {
	
	private String id;

	private String name;

	private String branchCode;

	private String address;

}
