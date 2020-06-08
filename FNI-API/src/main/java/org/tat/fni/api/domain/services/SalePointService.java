package org.tat.fni.api.domain.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tat.fni.api.domain.SalesPoints;
import org.tat.fni.api.domain.repository.SalePointRepository;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.ErrorCode;

@Service
public class SalePointService {

	@Autowired
	private SalePointRepository salePointRepository;

	@Transactional
	public List<SalesPoints> findAll() {
		return salePointRepository.findAll();
	}

	public List<Object[]> findAllNativeObject() {
		return salePointRepository.findAllNativeObject();
	}

	public List<Object> findAllColumnName() {
		return salePointRepository.findAllColumnName();
	}

	@Transactional
	public Optional<SalesPoints> findById(String id) throws DAOException {
		if (!StringUtils.isBlank(id)) {
			if (salePointRepository.findById(id).isPresent()) {
				return salePointRepository.findById(id);
			} else {
				throw new DAOException(ErrorCode.SYSTEM_ERROR_RESOURCE_NOT_FOUND, id + " not found in SalePoint");
			}
		} else {
			return Optional.empty();
		}

	}

}
