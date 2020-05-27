package org.tat.fni.api.domain.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tat.fni.api.domain.RiskyOccupation;
import org.tat.fni.api.exception.DAOException;

@Repository("RiskyOccupationDAO")
public class RiskyOccupationDAO extends BasicDAO implements IRiskyOccupationDAO{

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void insert(RiskyOccupation riskyOccupation) throws DAOException {
		try {
			em.persist(riskyOccupation);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert RiskyOccupation", pe);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void update(RiskyOccupation riskyOccupation) throws DAOException {
			try {
				em.merge(riskyOccupation);
				em.flush();
			} catch (PersistenceException pe) {
				throw translate("Failed to update RiskyOccupation", pe);
			}
			
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void delete(RiskyOccupation riskyOccupation) throws DAOException {
			try {
				riskyOccupation=em.merge(riskyOccupation);
				em.remove(riskyOccupation);
				em.flush();
			} catch (PersistenceException pe) {
				throw translate("Failed to delete RiskyOccupation", pe);
			}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Optional<RiskyOccupation> findById(String id) throws DAOException {
		RiskyOccupation result=null;
		try {
			result=em.find(RiskyOccupation.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find RiskyOccupation by Id", pe);
		}
		return Optional.of(result);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public List<RiskyOccupation> findAll() throws DAOException {
		List<RiskyOccupation> result=null;
		try {
			Query q=em.createNamedQuery("RiskyOccupation.findAll");
			result=q.getResultList();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of RiskyOccupation ", pe);
		}
		return result;
	}

}
