/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.tat.fni.api.domain.services.Interfaces;

import java.util.List;

import org.tat.fni.api.domain.Qualification;


public interface IQualificationService {
	public void addNewQualification(Qualification qualification);

	public void updateQualification(Qualification qualification);

	public void deleteQualification(Qualification qualification);

	public List<Qualification> findAllQualification();

	public List<Qualification> findByCriteria(String criteria);
}
