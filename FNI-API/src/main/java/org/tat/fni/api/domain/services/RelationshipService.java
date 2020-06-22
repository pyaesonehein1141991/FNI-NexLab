package org.tat.fni.api.domain.services;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tat.fni.api.domain.RelationShip;
import org.tat.fni.api.domain.repository.RelationshipRepository;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.ErrorCode;

@Service
public class RelationshipService {

	@Autowired
	private RelationshipRepository relationshipRepository;

	public List<RelationShip> findAll() {
		return relationshipRepository.findAll();
	}
	
	public List<Object[]> findAllNativeObject() {
		return relationshipRepository.findAllNativeObject();
	}

	public List<Object> findAllColumnName() {
		return relationshipRepository.findAllColumnName();
	}

	@Transactional
	public Optional<RelationShip> findById(String id) throws DAOException {
		if (!StringUtils.isBlank(id)) {
			if (relationshipRepository.findById(id).isPresent()) {
				return relationshipRepository.findById(id);
			} else {
				throw new DAOException(ErrorCode.SYSTEM_ERROR_RESOURCE_NOT_FOUND, id + " not found in relationship");
			}
		} else {
			return Optional.empty();
		}

	}

}
