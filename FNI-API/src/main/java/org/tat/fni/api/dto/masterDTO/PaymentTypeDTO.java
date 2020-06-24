package org.tat.fni.api.dto.masterDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentTypeDTO {
	
	private String id;

	private String name;

	private int month;

	private String description;

}
