package org.tat.fni.api.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tat.fni.api.domain.Hospital;
import org.tat.fni.api.domain.repository.HospitalRepository;

@Service
public class HospitalService {

	@Autowired
	private HospitalRepository hospitalRepository;

	public List<Hospital> findAll() {
		return hospitalRepository.findAll();
	}

	public List<Object[]> findAllNativeObject() {
		return hospitalRepository.findAllNativeObject();
	}

	public List<Object> findAllColumnName() {
		return hospitalRepository.findAllColumnName();
	}

}
