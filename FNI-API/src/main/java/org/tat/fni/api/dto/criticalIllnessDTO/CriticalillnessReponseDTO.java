package org.tat.fni.api.dto.criticalIllnessDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CriticalillnessReponseDTO {

  private String proposalID;
  private String proposalNo;
  private double proposedPremium;



}
