package org.tat.fni.api.common.emumdata;

public enum ProposalType {

	UNDERWRITING("UNDERWRITING"), ENDORSEMENT("ENDORSEMENT"), RENEWAL("RENEWAL"), TERMINATE("TERMINATE"), PAIDUP("PAID_UP");

	private String label;

	private ProposalType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
