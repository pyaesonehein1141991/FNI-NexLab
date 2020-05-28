package org.tat.fni.api.dto.personalAccidentDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonalAccidentReponseDTO {

  private String proposalID;
  private String proposalNo;
  private double proposedPremium;

}


