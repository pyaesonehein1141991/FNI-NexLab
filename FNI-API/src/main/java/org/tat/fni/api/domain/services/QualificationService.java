/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.tat.fni.api.domain.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tat.fni.api.domain.Qualification;
import org.tat.fni.api.domain.repository.IQualificationDAO;
import org.tat.fni.api.domain.services.Interfaces.IQualificationService;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.SystemException;

@Service(value = "QualificationService")
public class QualificationService extends BaseService implements IQualificationService {

	@Resource(name = "QualificationDAO")
	private IQualificationDAO qualificationDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewQualification(Qualification qualification) {
		try {
			qualificationDAO.insert(qualification);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Qualification", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateQualification(Qualification qualification) {
		try {
			qualificationDAO.update(qualification);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a Qualification", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteQualification(Qualification qualification) {
		try {
			qualificationDAO.delete(qualification);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a Qualification", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Qualification> findAllQualification() {
		List<Qualification> result = null;
		try {
			result = qualificationDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of Qualification)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Qualification findQualificationById(String id) {
		Qualification result = null;
		try {
			result = qualificationDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Qualification (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Qualification> findByCriteria(String criteria) {
		List<Qualification> result = null;
		try {
			result = qualificationDAO.findByCriteria(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Qualification by criteria " + criteria, e);
		}
		return result;
	}

}