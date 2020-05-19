package org.tat.fni.api.dto.studentLifeDTO;



import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentLifeReponseDTO {

	private String bpmsInsuredPersonId;
	private String policyNo;
	private String proposalNo;
	private String customerId;

}
