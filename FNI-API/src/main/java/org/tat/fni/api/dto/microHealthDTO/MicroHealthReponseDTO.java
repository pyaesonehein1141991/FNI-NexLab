package org.tat.fni.api.dto.microHealthDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MicroHealthReponseDTO {

  private String proposalID;
  private String proposalNo;
  private double proposedPremium;



}
