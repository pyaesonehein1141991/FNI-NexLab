/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.tat.fni.api.common.emumdata;

public enum PaymentChannel {
	CASHED("Cash"), TRANSFER("Transfer"), CHEQUE("Cheque");

	private String label;

	private PaymentChannel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}