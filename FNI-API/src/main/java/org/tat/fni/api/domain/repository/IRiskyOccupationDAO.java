package org.tat.fni.api.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tat.fni.api.domain.RiskyOccupation;
import org.tat.fni.api.exception.DAOException;


public interface IRiskyOccupationDAO {
	
	public void insert(RiskyOccupation riskyOccupation) throws DAOException;
	
	public void update(RiskyOccupation riskyOccupation) throws DAOException;
	
	public void delete(RiskyOccupation riskyOccupation) throws DAOException;
	
	public Optional<RiskyOccupation> findById(String id) throws DAOException;
	
	public List<RiskyOccupation> findAll() throws DAOException;
	

}
