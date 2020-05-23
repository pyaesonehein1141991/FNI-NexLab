package org.tat.fni.api.dto.shortTermEndowmentLifeDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShortTermEndowmentLifeReponseDTO {

  private String proposalID;
  private String proposalNo;
  private double proposedPremium;

}


