package org.tat.fni.api.domain;

import java.util.Map;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tat.fni.api.common.KeyFactor;
import org.tat.fni.api.common.emumdata.KeyFactorType;
import org.tat.fni.api.domain.addon.AddOn;
import org.tat.fni.api.domain.repository.BasicDAO;
import org.tat.fni.api.exception.DAOException;

@Repository("PremiumCalculatorDAO")
public class PremiumCalculatorDAO extends BasicDAO implements IPremiumCalculatorDAO {

  @Override
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public <T> Double findPremiumRate(Map<KeyFactor, String> keyfatorValueMap, T param)
      throws DAOException {
    Double result = null;
    try {
      String premiumRateId = null;
      String referenceId = null;
      boolean isProduct = true;
      if (param instanceof Product) {
        Product product = (Product) param;
        isProduct = true;
        referenceId = product.getId();
      } else if (param instanceof AddOn) {
        AddOn addOn = (AddOn) param;
        isProduct = false;
        referenceId = addOn.getId();
      }

      if (!keyfatorValueMap.isEmpty()) {
        premiumRateId = findProductPremiumRateId(keyfatorValueMap, referenceId, isProduct);
      }

      StringBuffer buffer = new StringBuffer(" SELECT r.premiumRate FROM ProductPremiumRate r ");
      if (isProduct) {
        buffer.append(" WHERE r.product.id = :referenceId ");
      } else {
        buffer.append(" WHERE r.addOn.id = :referenceId ");
      }

      if (!keyfatorValueMap.isEmpty()) {
        buffer.append(" AND r.id = :premiumRateId ");
      }

      Query query = em.createQuery(buffer.toString());
      query.setParameter("referenceId", referenceId);
      if (!keyfatorValueMap.isEmpty())
        query.setParameter("premiumRateId", premiumRateId);
      result = (Double) query.getSingleResult();

    } catch (NoResultException e) {
      return null;
    } catch (PersistenceException pe) {
      throw translate("Failed to find ProductPremiumRate", pe);
    }
    return result;
  }

  @Transactional(propagation = Propagation.REQUIRED)
  private String findProductPremiumRateId(Map<KeyFactor, String> keyfatorValueMap,
      String referenceId, boolean isProduct) throws DAOException {
    String result = null;
    try {
      KeyFactorType keyFactorType = null;
      String value = null;
      String referenceObject = isProduct ? "product" : "addOn";
      StringBuffer buffer = new StringBuffer();
      buffer.append(" SELECT r.id FROM ProductPremiumRate r");
      for (int i = 0; i < keyfatorValueMap.size(); i++) {
        buffer.append(" JOIN r.premiumRateKeyFactor k_" + i);
      }
      buffer.append(" WHERE r.id is not NULL");
      int c = 0;
      String keyfactorValue;
      for (KeyFactor kf : keyfatorValueMap.keySet()) {
        keyfactorValue = kf.getValue().replace(" ", "");
        keyFactorType = kf.getKeyFactorType();
        buffer.append(" AND k_" + c + ".keyFactor.id = :" + keyfactorValue + "Id");
        if (!keyFactorType.equals(KeyFactorType.FROM_TO)) {
          buffer.append(" AND k_" + c + ".value = :" + keyfactorValue);
        } else {
          buffer.append(" AND k_" + c + ".from <= :" + keyfactorValue + " AND k_" + c + ".to >= :"
              + keyfactorValue);
        }
        c++;
      }
      buffer.append(" AND r." + referenceObject + ".id = :referenceId");

      Query query = em.createQuery(buffer.toString());
      c = 0;
      for (KeyFactor kf : keyfatorValueMap.keySet()) {
        value = keyfatorValueMap.get(kf);
        keyfactorValue = kf.getValue().replace(" ", "");
        keyFactorType = kf.getKeyFactorType();
        query.setParameter(keyfactorValue + "Id", kf.getId());
        query.setParameter(keyfactorValue,
            keyFactorType.equals(KeyFactorType.FROM_TO) ? Double.parseDouble(value) : value);
      }
      query.setParameter("referenceId", referenceId);

      result = (String) query.getSingleResult();

    } catch (NoResultException e) {
      throw translate("There is no product premium rate for these keyfactors, insert please!", e);
    } catch (PersistenceException pe) {
      throw translate("Failed to find ProductPremiumRate", pe);
    }

    return result;
  }
}
