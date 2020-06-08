package org.tat.fni.api.domain.services;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tat.fni.api.domain.Bank;
import org.tat.fni.api.domain.repository.BankRepository;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.ErrorCode;

@Service
public class BankService {

	@Autowired
	private BankRepository bankRepository;

	public List<Bank> findAll() {
		return bankRepository.findAll();
	}

	public List<Object[]> findAllNativeObject() {
		return bankRepository.findAllNativeObject();
	}

	public List<Object> findAllColumnName() {
		return bankRepository.findAllColumnName();
	}

	@Transactional
	public Optional<Bank> findById(String id) throws DAOException {
		if (!StringUtils.isBlank(id)) {
			if (bankRepository.findById(id).isPresent()) {
				return bankRepository.findById(id);
			} else {
				throw new DAOException(ErrorCode.SYSTEM_ERROR_RESOURCE_NOT_FOUND, id + " not found in bank");
			}
		} else {
			return Optional.empty();
		}

	}

}
