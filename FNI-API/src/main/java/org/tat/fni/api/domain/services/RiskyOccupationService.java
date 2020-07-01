package org.tat.fni.api.domain.services;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tat.fni.api.domain.RiskyOccupation;
import org.tat.fni.api.domain.repository.IRiskyOccupationDAO;
import org.tat.fni.api.domain.services.Interfaces.IRiskyOccupationService;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.SystemException;

@Service(value = "RiskyOccupationService")
public class RiskyOccupationService extends BaseService implements IRiskyOccupationService {

	@Resource(name = "RiskyOccupationDAO")
	private IRiskyOccupationDAO riskyOccupationDAO;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewRiskyOccupation(RiskyOccupation riskyOccupation) {
		try {
			riskyOccupationDAO.insert(riskyOccupation);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new RiskyOccupation", e);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateRiskyOccupation(RiskyOccupation riskyOccupation) {
		try {
			riskyOccupationDAO.update(riskyOccupation);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update RiskyOccupation", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteRiskyOccupation(RiskyOccupation riskyOccupation) {
		try {
			riskyOccupationDAO.delete(riskyOccupation);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete RiskyOccupation", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Optional<RiskyOccupation> findRiskyOccupationById(String id) {
		Optional<RiskyOccupation> result = null;
		try {
			result = riskyOccupationDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find RiskyOccupation by Id", e);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<RiskyOccupation> findAllRiskyOccupation() {
		List<RiskyOccupation> result = null;
		try {
			result = riskyOccupationDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find All RiskyOccupation", e);
		}
		return result;
	}

}
