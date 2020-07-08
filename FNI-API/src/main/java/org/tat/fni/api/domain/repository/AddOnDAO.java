/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.tat.fni.api.domain.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tat.fni.api.domain.addon.AddOn;
import org.tat.fni.api.exception.DAOException;

@Repository("AddOnDAO")
public class AddOnDAO extends BasicDAO implements IAddOnDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(AddOn addOn) throws DAOException {
		try {
			em.persist(addOn);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert AddOn", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(AddOn addOn) throws DAOException {
		try {
			em.merge(addOn);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update AddOn", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(AddOn addOn) throws DAOException {
		try {
			addOn = em.merge(addOn);
			em.remove(addOn);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update AddOn", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public AddOn findById(String id) throws DAOException {
		AddOn result = null;
		try {
			result = em.find(AddOn.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find AddOn", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AddOn> findAll() throws DAOException {
		List<AddOn> result = null;
		try {
			Query q = em.createNamedQuery("AddOn.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of AddOn", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AddOn> findByCriteria(String criteria) throws DAOException {
		List<AddOn> result = null;
		try {
			Query q = em.createQuery("Select a from AddOn a where a.name Like '" + criteria + "%'");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find by criteria of AddOn.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AddOn> findPremiumRateOfAddOn() throws DAOException {
		List<AddOn> result = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("Select a ,r.premiumRate");
			buffer.append(" from AddOn a join ProductPremiumRate r on a.id = r.addon.id ");
			Query query = em.createQuery(buffer.toString());
			result = query.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Premium Rate of AddOn.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AddOn> findAddOnByProductId(String productId) throws DAOException {
		List<Object[]> objectList = new ArrayList<Object[]>();
		Map<String, AddOn> resultMap = new HashMap<String, AddOn>();
		AddOn addon;
		double premiumRate;
		try {
			String nativeQuery = "";
			Query query = em.createNativeQuery(nativeQuery);
			query.setParameter(1, productId);

			objectList = query.getResultList();
			for (Object[] b : objectList) {
				addon = (AddOn) b[0];
				premiumRate = (double) b[1];
				resultMap.put(addon.getId(), new AddOn(addon, premiumRate));
			}
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Premium Rate of AddOn.", pe);
		}
		return new ArrayList<AddOn>(resultMap.values());
	}
}
