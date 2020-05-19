package org.tat.fni.api.common.emumdata;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "idType")
@XmlEnum
public enum IdType {
	NRCNO("NRCNO"), FRCNO("FRCNO"), PASSPORTNO("PASSPORTNO"), STILL_APPLYING("Still Applying");

	private String label;

	private IdType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
