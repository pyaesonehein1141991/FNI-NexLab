package org.tat.fni.api.dto;

import java.util.ArrayList;
import java.util.List;

import org.tat.fni.api.common.CommonDTO;
import org.tat.fni.api.common.interfaces.ISorter;
import org.tat.fni.api.domain.MedicalKeyFactorValue;
import org.tat.fni.api.domain.addon.AddOn;

public class MedProInsuAddOnDTO extends CommonDTO implements ISorter {
	private static final long serialVersionUID = 1L;
	private AddOn addOn;
	private int unit;
	private double premium;
	private double sumInsured;
	private boolean include;
	private List<MedicalKeyFactorValue> keyFactorValueList;

	public MedProInsuAddOnDTO() {
	}

	public MedProInsuAddOnDTO(AddOn addOn) {
		this.addOn = addOn;
		this.include = addOn.isCompulsory();
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public AddOn getAddOn() {
		return addOn;
	}

	public void setAddOn(AddOn addOn) {
		this.addOn = addOn;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public boolean isInclude() {
		return include;
	}

	public void setInclude(boolean include) {
		this.include = include;
	}

	public List<MedicalKeyFactorValue> getKeyFactorValueList() {
		if (keyFactorValueList == null)
			keyFactorValueList = new ArrayList<MedicalKeyFactorValue>();
		return keyFactorValueList;
	}

	public void setKeyFactorValueList(List<MedicalKeyFactorValue> keyFactorValueList) {
		this.keyFactorValueList = keyFactorValueList;
	}

	public void addKeyFactorValue(MedicalKeyFactorValue kfv) {
		getKeyFactorValueList().add(kfv);
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	@Override
	public String getRegistrationNo() {
		if (addOn != null)
			return addOn.getName();
		return "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((addOn == null) ? 0 : addOn.hashCode());
		long temp;
		temp = Double.doubleToLongBits(premium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + unit;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MedProInsuAddOnDTO other = (MedProInsuAddOnDTO) obj;
		if (addOn == null) {
			if (other.addOn != null)
				return false;
		} else if (!addOn.equals(other.addOn))
			return false;
		if (Double.doubleToLongBits(premium) != Double.doubleToLongBits(other.premium))
			return false;
		if (unit != other.unit)
			return false;
		return true;
	}

}
