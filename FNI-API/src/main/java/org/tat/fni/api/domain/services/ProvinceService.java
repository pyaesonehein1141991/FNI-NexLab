package org.tat.fni.api.domain.services;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tat.fni.api.domain.Province;
import org.tat.fni.api.domain.repository.ProvinceRepository;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.ErrorCode;

@Service
public class ProvinceService {
	
	@Autowired
	private ProvinceRepository provinceRepository;

	public List<Province> findAll() {
		return provinceRepository.findAll();
	}

	public List<Object[]> findAllNativeObject() {
		return provinceRepository.findAllNativeObject();
	}

	public List<Object> findAllColumnName() {
		return provinceRepository.findAllColumnName();
	}

	@Transactional
	public Optional<Province> findById(String id) throws DAOException {
		if (!StringUtils.isBlank(id)) {
			if (provinceRepository.findById(id).isPresent()) {
				return provinceRepository.findById(id);
			} else {
				throw new DAOException(ErrorCode.SYSTEM_ERROR_RESOURCE_NOT_FOUND, id + " not found in PamentType");
			}
		} else {
			return Optional.empty();
		}

	}

}
