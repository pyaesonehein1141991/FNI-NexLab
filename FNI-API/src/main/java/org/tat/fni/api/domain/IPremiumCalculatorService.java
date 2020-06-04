package org.tat.fni.api.domain;

import java.util.Map;
import org.tat.fni.api.common.KeyFactor;
import org.tat.fni.api.exception.SystemException;



public interface IPremiumCalculatorService {

  public <T> Double calculatePremium(Map<KeyFactor, String> keyfatorValueMap, T param,
      PremiumCalData data) throws SystemException;

  public <T> Double calulatePremium(double premiumRate, T param, PremiumCalData data)
      throws SystemException;

  public <T> Double findPremiumRate(Map<KeyFactor, String> keyfatorValueMap, T param)
      throws SystemException;

  public Double calculateStampFee(Product param, PremiumCalData data) throws SystemException;
}
