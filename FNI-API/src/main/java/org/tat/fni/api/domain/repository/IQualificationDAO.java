/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.tat.fni.api.domain.repository;

import java.util.List;

import org.tat.fni.api.domain.Qualification;
import org.tat.fni.api.exception.DAOException;


public interface IQualificationDAO {
	public void insert(Qualification qualification) throws DAOException;

	public void update(Qualification qualification) throws DAOException;

	public void delete(Qualification qualification) throws DAOException;

	public Qualification findById(String id) throws DAOException;

	public List<Qualification> findAll() throws DAOException;

	public List<Qualification> findByCriteria(String criteria) throws DAOException;
}
