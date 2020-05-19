package org.tat.fni.api.dto.publicTermLife;



import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PublicTermLifeReponseDTO {

	private String bpmsInsuredPersonId;
	private String policyNo;
	private String proposalNo;
	private String customerId;

}
