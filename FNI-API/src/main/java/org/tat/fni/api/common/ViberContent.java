package org.tat.fni.api.common;
import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ViberContent implements Serializable {
	private static final long serialVersionUID = 1L;
	private String viberPhone;

	public String getViberPhone() {
		return viberPhone;
	}

	public void setViberPhone(String viberPhone) {
		this.viberPhone = viberPhone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((viberPhone == null) ? 0 : viberPhone.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ViberContent other = (ViberContent) obj;
		if (viberPhone == null) {
			if (other.viberPhone != null)
				return false;
		} else if (!viberPhone.equals(other.viberPhone))
			return false;
		return true;
	}
	
	
	
}
