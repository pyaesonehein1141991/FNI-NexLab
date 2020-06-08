package org.tat.fni.api.domain.services;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tat.fni.api.domain.Country;
import org.tat.fni.api.domain.repository.CountryRepository;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.ErrorCode;

@Service
public class CountryService {

	@Autowired
	private CountryRepository countryRepository;

	public List<Country> findAll() {
		return countryRepository.findAll();
	}

	public List<Object[]> findAllNativeObject() {
		return countryRepository.findAllNativeObject();
	}

	public List<Object> findAllColumnName() {
		return countryRepository.findAllColumnName();
	}

	@Transactional
	public Optional<Country> findById(String id) throws DAOException {
		if (!StringUtils.isBlank(id)) {
			if (countryRepository.findById(id).isPresent()) {
				return countryRepository.findById(id);
			} else {
				throw new DAOException(ErrorCode.SYSTEM_ERROR_RESOURCE_NOT_FOUND, id + " not found in Country");
			}
		} else {
			return Optional.empty();
		}

	}
}
