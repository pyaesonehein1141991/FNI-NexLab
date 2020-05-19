package org.tat.fni.api.common;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.tat.fni.api.common.emumdata.NcbYear;

@Embeddable
public class NcbRate implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Enumerated(EnumType.STRING)
	private NcbYear ncbYear;
	private float ncbPercentage;
	
	public NcbRate() {}
	
	public NcbYear getNcbYear() {
		return ncbYear;
	}

	public void setNcbYear(NcbYear ncbYear) {
		this.ncbYear = ncbYear;
	}

	public float getNcbPercentage() {
		return ncbPercentage;
	}

	public void setNcbPercentage(float ncbPercentage) {
		this.ncbPercentage = ncbPercentage;
	}

	@Override
	public boolean equals(Object object) {
		return EqualsBuilder.reflectionEquals(this, object);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
