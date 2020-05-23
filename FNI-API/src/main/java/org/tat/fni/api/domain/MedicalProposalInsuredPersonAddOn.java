package org.tat.fni.api.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;
import org.tat.fni.api.common.interfaces.ISorter;
import org.tat.fni.api.domain.addon.AddOn;



@Entity
@Table(name = TableName.MEDICALPROPOSALINSUREDPERSONADDON)
@TableGenerator(name = "MEDINSUREDPERSONADDON_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "MEDINSUREDPERSONADDON_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class MedicalProposalInsuredPersonAddOn implements ISorter {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "MEDINSUREDPERSONADDON_GEN")
	private String id;

	private int unit;
	private double sumInsured;
	private double premium;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEDICALPRODUCTADDONID", referencedColumnName = "ID")
	private AddOn addOn;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "REFERENCEID", referencedColumnName = "ID")
	private List<MedicalKeyFactorValue> keyFactorValueList;

	@Embedded
	private UserRecorder recorder;

	@Version
	private int version;

	public MedicalProposalInsuredPersonAddOn() {
	}

	public MedicalProposalInsuredPersonAddOn(MedicalProposalInsuredPersonAddOn addOn) {
		this.id = addOn.getId();
		this.addOn = addOn.getAddOn();
		this.unit = addOn.getUnit();
		this.sumInsured = addOn.getSumInsured();
		this.premium = addOn.getPremium();
		for (MedicalKeyFactorValue kfv : addOn.getKeyFactorValueList()) {
			addKeyFactorValue(new MedicalKeyFactorValue(kfv));
		}
		this.version = addOn.getVersion();
	}

//	public MedicalProposalInsuredPersonAddOn(MedicalPolicyInsuredPersonAddOn policyAddOn) {
//		this.addOn = policyAddOn.getAddOn();
//		this.unit = policyAddOn.getUnit();
//		this.sumInsured = policyAddOn.getSumInsured();
//		this.premium = policyAddOn.getPremium();
//		for (MedicalPolicyKeyFactorValue kfv : policyAddOn.getMedicalKeyFactorValueList()) {
//			addKeyFactorValue(new MedicalKeyFactorValue(kfv));
//		}
//	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AddOn getAddOn() {
		if (addOn == null) {
			addOn = new AddOn();
		}
		return addOn;
	}

	public void setAddOn(AddOn addOn) {
		this.addOn = addOn;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
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

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
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
		int result = 1;
		result = prime * result + ((addOn == null) ? 0 : addOn.hashCode());
		long temp;
		temp = Double.doubleToLongBits(premium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		temp = Double.doubleToLongBits(sumInsured);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + unit;
		result = prime * result + version;
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
		MedicalProposalInsuredPersonAddOn other = (MedicalProposalInsuredPersonAddOn) obj;
		if (addOn == null) {
			if (other.addOn != null)
				return false;
		} else if (!addOn.equals(other.addOn))
			return false;
		if (Double.doubleToLongBits(premium) != Double.doubleToLongBits(other.premium))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (Double.doubleToLongBits(sumInsured) != Double.doubleToLongBits(other.sumInsured))
			return false;
		if (unit != other.unit)
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
