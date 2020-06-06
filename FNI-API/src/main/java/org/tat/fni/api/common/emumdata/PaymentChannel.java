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