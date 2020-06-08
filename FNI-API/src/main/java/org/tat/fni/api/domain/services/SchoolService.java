package org.tat.fni.api.domain.services;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tat.fni.api.domain.School;
import org.tat.fni.api.domain.repository.SchoolRepository;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.ErrorCode;

@Service
public class SchoolService {

	@Autowired
	private SchoolRepository schoolRepository;

	public List<School> findAll() {
		return schoolRepository.findAll();
	}

	public List<Object[]> findAllNativeObject() {
		return schoolRepository.findAllNativeObject();
	}

	public List<Object> findAllColumnName() {
		return schoolRepository.findAllColumnName();
	}

	@Transactional
	public Optional<School> findById(String id) throws DAOException {
		if (!StringUtils.isBlank(id)) {
			if (schoolRepository.findById(id).isPresent()) {
				return schoolRepository.findById(id);
			} else {
				throw new DAOException(ErrorCode.SYSTEM_ERROR_RESOURCE_NOT_FOUND, id + " not found in School");
			}
		} else {
			return Optional.empty();
		}

	}

}
