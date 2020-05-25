package org.tat.fni.api.dto.healthInsuranceDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IndividualHealthReponseDTO {

  private String proposalID;
  private String proposalNo;
  private double proposedPremium;



}
