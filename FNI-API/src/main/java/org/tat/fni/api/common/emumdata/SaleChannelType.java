package org.tat.fni.api.common.emumdata;

public enum SaleChannelType {
	AGENT("Agent"), BANK("Bank"), DIRECTMARKETING("Direct Marketing"), AFP("AFP"), WALKIN("Walkin"), COINSURANCE_INWARD("Coinsurance Inward"), REINSURANCE("Reinsurance Inward");

	private String label;

	private SaleChannelType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
