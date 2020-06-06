/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.tat.fni.api.common.emumdata;

public enum Gender {
	FEMALE("Female"), MALE("Male");

	private String label;

	private Gender(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}