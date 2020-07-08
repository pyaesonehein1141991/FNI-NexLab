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
import org.tat.fni.api.domain.Qualification;
import org.tat.fni.api.exception.DAOException;

@Repository("QualificationDAO")
public class QualificationDAO extends BasicDAO implements IQualificationDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(Qualification qualification) throws DAOException {
		try {
			em.persist(qualification);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Qualification", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Qualification qualification) throws DAOException {
		try {
			em.merge(qualification);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Qualification", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Qualification qualification) throws DAOException {
		try {
			qualification = em.merge(qualification);
			em.remove(qualification);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Qualification", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Qualification findById(String id) throws DAOException {
		Qualification result = null;
		try {
			result = em.find(Qualification.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Qualification", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Qualification> findAll() throws DAOException {
		List<Qualification> result = null;
		try {
			Query q = em.createNamedQuery("Qualification.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Qualification", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Qualification> findByCriteria(String criteria) throws DAOException {
		List<Qualification> result = null;
		try {
			// Query q = em.createNamedQuery("Qualification.findByCriteria");
			Query q = em.createQuery("Select t from Qualification t where t.name Like '" + criteria + "%'");
			// q.setParameter("criteriaValue", "%" + criteria + "%");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find by criteria of Qualification.", pe);
		}
		return result;
	}

}
