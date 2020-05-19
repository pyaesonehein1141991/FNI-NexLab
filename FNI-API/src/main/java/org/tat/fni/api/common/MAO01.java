package org.tat.fni.api.common;

/*
 * @purpose used for display addon info detail
 */
public class MAO01 {
	private String addOnName;
	private int unit;
	private double premium;

	public MAO01() {

	}

	public MAO01(String addOnName, int unit, double premium) {
		super();
		this.addOnName = addOnName;
		this.unit = unit;
		this.premium = premium;
	}

	public String getAddOnName() {
		return addOnName;
	}

	public void setAddOnName(String addOnName) {
		this.addOnName = addOnName;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

}
