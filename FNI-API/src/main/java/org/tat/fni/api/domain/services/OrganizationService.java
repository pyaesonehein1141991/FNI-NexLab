package org.tat.fni.api.domain.services;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tat.fni.api.domain.Organization;
import org.tat.fni.api.domain.repository.OrganizationRepository;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.ErrorCode;

@Service
public class OrganizationService {

	@Autowired
	private OrganizationRepository organizationRepository;

	public List<Organization> findAll() {
		return organizationRepository.findAll();
	}

	public List<Object[]> findAllNativeObject() {
		return organizationRepository.findAllNativeObject();
	}

	public List<Object> findAllColumnName() {
		return organizationRepository.findAllColumnName();
	}

	@Transactional
	public Optional<Organization> findById(String id) throws DAOException {
		if (!StringUtils.isBlank(id)) {
			if (organizationRepository.findById(id).isPresent()) {
				return organizationRepository.findById(id);
			} else {
				throw new DAOException(ErrorCode.SYSTEM_ERROR_RESOURCE_NOT_FOUND, id + " not found in Organization");
			}
		} else {
			return Optional.empty();
		}

	}
}
