/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.tat.fni.api.domain.repository;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tat.fni.api.domain.Industry;
import org.tat.fni.api.exception.DAOException;

@Repository("IndustryDAO")
public class IndustryDAO extends BasicDAO implements IIndustryDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(Industry Industry) throws DAOException {
		try {
			em.persist(Industry);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Industry", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Industry Industry) throws DAOException {
		try {
			em.merge(Industry);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Industry", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Industry Industry) throws DAOException {
		try {
			Industry = em.merge(Industry);
			em.remove(Industry);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Industry", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Industry findById(String id) throws DAOException {
		Industry result = null;
		try {
			result = em.find(Industry.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Industry", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Industry> findAll() throws DAOException {
		List<Industry> result = null;
		try {
			Query q = em.createNamedQuery("Industry.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Industry", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Industry> findByCriteria(String criteria) throws DAOException {
		List<Industry> result = null;
		try {
			// Query q = em.createNamedQuery("Industry.findByCriteria");
			Query q = em.createQuery("Select t from Industry t where t.name Like '" + criteria + "%'");
			// q.setParameter("criteriaValue", "%" + criteria + "%");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find by criteria of Industry.", pe);
		}
		return result;
	}
}
