package org.tat.fni.api.domain.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tat.fni.api.domain.Product;
import org.tat.fni.api.domain.repository.ProductRepository;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.ErrorCode;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public List<Object[]> findAllNativeObject() {
		return productRepository.findAllNativeObject();
	}

	public List<Object> findAllColumnName() {
		return productRepository.findAllColumnName();
	}

	@Transactional
	public Optional<Product> findById(String id) throws DAOException {
		if (!StringUtils.isBlank(id)) {
			if (productRepository.findById(id).isPresent()) {
				return productRepository.findById(id);
			} else {
				throw new DAOException(ErrorCode.SYSTEM_ERROR_RESOURCE_NOT_FOUND, id + " not found in Product");
			}
		} else {
			return Optional.empty();
		}

	}

}
