package org.tat.fni.api.domain;

import java.util.Map;
import org.tat.fni.api.common.KeyFactor;
import org.tat.fni.api.exception.DAOException;



public interface IPremiumCalculatorDAO {

  public <T> Double findPremiumRate(Map<KeyFactor, String> keyfatorValueMap, T param)
      throws DAOException;

}
