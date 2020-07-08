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

import org.tat.fni.api.domain.Industry;


public interface IIndustryService {
	public void addNewIndustry(Industry Industry);

	public void updateIndustry(Industry Industry);

	public void deleteIndustry(Industry Industry);

	public Industry findIndustryById(String id);

	public List<Industry> findAllIndustry();

	public List<Industry> findByCriteria(String criteria);
}
