package org.tat.fni.api.domain.services;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tat.fni.api.domain.Religion;
import org.tat.fni.api.domain.repository.ReligionRepository;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.ErrorCode;

@Service
public class ReligionService {
	
	@Autowired
	private ReligionRepository religionRepository;

	public List<Religion> findAll() {
		return religionRepository.findAll();
	}

	public List<Object[]> findAllNativeObject() {
		return religionRepository.findAllNativeObject();
	}

	public List<Object> findAllColumnName() {
		return religionRepository.findAllColumnName();
	}

	@Transactional
	public Optional<Religion> findById(String id) throws DAOException {
		if (!StringUtils.isBlank(id)) {
			if (religionRepository.findById(id).isPresent()) {
				return religionRepository.findById(id);
			} else {
				throw new DAOException(ErrorCode.SYSTEM_ERROR_RESOURCE_NOT_FOUND, id + " not found in Religion");
			}
		} else {
			return Optional.empty();
		}

	}

}
