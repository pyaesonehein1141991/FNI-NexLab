package org.tat.fni.api.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tat.fni.api.domain.Province;
import org.tat.fni.api.domain.repository.ProvinceRepository;

@Service
public class ProvienceServices {

	@Autowired
	private ProvinceRepository provienceRepository;

	public List<Province> findAll() {
		return provienceRepository.findAll();
	}

	public List<Object[]> findAllNativeObject() {
		return provienceRepository.findAllNativeObject();
	}

	public List<Object> findAllColumnName() {
		return provienceRepository.findAllColumnName();
	}

}
