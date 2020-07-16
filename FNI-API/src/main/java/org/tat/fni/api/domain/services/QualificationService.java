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
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tat.fni.api.domain.Qualification;
import org.tat.fni.api.domain.repository.QualificationRepository;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.ErrorCode;

@Service(value = "QualificationService")
public class QualificationService {

	@Autowired
	private QualificationRepository qualificationRepository;

	public List<Qualification> findAll() {
		return qualificationRepository.findAll();
	}

	@Transactional
	public Optional<Qualification> findById(String id) throws DAOException {
		if (!StringUtils.isBlank(id)) {
			if (qualificationRepository.findById(id).isPresent()) {
				return qualificationRepository.findById(id);
			} else {
				throw new DAOException(ErrorCode.SYSTEM_ERROR_RESOURCE_NOT_FOUND, id + " not found in Religion");
			}
		} else {
			return Optional.empty();
		}

	}

}