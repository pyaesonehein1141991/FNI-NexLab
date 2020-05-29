package org.tat.fni.api.dto.sportManDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SportManReponseDTO {

  private String proposalID;
  private String proposalNo;
  private double proposedPremium;

}


