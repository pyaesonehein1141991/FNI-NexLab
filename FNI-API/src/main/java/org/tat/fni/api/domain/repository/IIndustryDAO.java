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

import org.tat.fni.api.domain.Industry;
import org.tat.fni.api.exception.DAOException;


public interface IIndustryDAO {
	public void insert(Industry Industry) throws DAOException;

	public void update(Industry Industry) throws DAOException;

	public void delete(Industry Industry) throws DAOException;

	public Industry findById(String id) throws DAOException;

	public List<Industry> findAll() throws DAOException;

	public List<Industry> findByCriteria(String criteria) throws DAOException;
}
