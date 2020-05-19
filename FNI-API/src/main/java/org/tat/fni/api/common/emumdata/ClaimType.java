package org.tat.fni.api.common.emumdata;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "claimType")
@XmlEnum
public enum ClaimType {
	HOSPITALIZED_CLAIM("HOSPITALIZED_CLAIM"), DEATH_CLAIM("DEATH_CLAIM"), MEDICATION_CLAIM("MEDICATION_CLAIM"), OPERATION_CLAIM("OPERATION_CLAIM");

	private String label;

	private ClaimType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
