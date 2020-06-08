package org.tat.fni.api.domain.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tat.fni.api.domain.Township;
import org.tat.fni.api.domain.repository.TownshipRepository;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.ErrorCode;

@Service
public class TownShipService {

	@Autowired
	private TownshipRepository townshipRepository;

	@Transactional
	public List<Township> findAll() {
		return townshipRepository.findAll();
	}

	public List<Object[]> findAllNativeObject() {
		return townshipRepository.findAllNativeObject();
	}

	public List<Object> findAllColumnName() {
		return townshipRepository.findAllColumnName();
	}

	@Transactional
	public Optional<Township> findById(String id) throws DAOException {
		if (!StringUtils.isBlank(id)) {
			if (townshipRepository.findById(id).isPresent()) {
				return townshipRepository.findById(id);
			} else {
				throw new DAOException(ErrorCode.SYSTEM_ERROR_RESOURCE_NOT_FOUND, id + " not found in Twonship");
			}
		} else {
			return Optional.empty();
		}

	}

}
