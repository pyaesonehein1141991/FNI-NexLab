/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.tat.fni.api.common;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "maritalStatus")
@XmlEnum
public enum MaritalStatus {
	MARRIED("Married"), SINGLE("Single"),DIVORCED("Divorced");

	private String label;

	private MaritalStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}