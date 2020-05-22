/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.tat.fni.api.domain.addon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.KeyFactor;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;
import org.tat.fni.api.common.emumdata.PremiumRateType;
import org.tat.fni.api.common.emumdata.ProductBaseType;
import org.tat.fni.api.domain.ProductContent;



@Entity
@Table(name = TableName.ADDON)
@TableGenerator(name = "ADDON_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "ADDON_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "AddOn.findAll", query = "SELECT a FROM AddOn a ORDER BY a.id ASC"),
		@NamedQuery(name = "AddOn.findById", query = "SELECT a FROM AddOn a WHERE a.id = :id") })
@EntityListeners(IDInterceptor.class)
public class AddOn implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ADDON_GEN")
	private String id;

	@Column(name = "MAXIMUMVALUE")
	private double maxValue;
	@Column(name = "MINIMUMVALUE")
	private double minValue;
	private boolean isBaseOnKeyFactor;
	private boolean isCompulsory;
	private double basedAmount;
	private double sumInsuredPerUnit;

	@Enumerated(EnumType.STRING)
	private PremiumRateType premiumRateType;

	@Enumerated(EnumType.STRING)
	private ProductBaseType productBaseType;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTCONTENTID", referencedColumnName = "ID")
	private ProductContent productContent;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ADDON_KEYFACTOR_LINK", joinColumns = { @JoinColumn(name = "ADDONID", referencedColumnName = "ID") }, inverseJoinColumns = {
			@JoinColumn(name = "KEYFACTORID", referencedColumnName = "ID") })
	private List<KeyFactor> keyFactorList;
	@Version
	private int version;

	@Embedded
	private UserRecorder recorder;

	@Transient
	private boolean include;

	public AddOn() {
	}

	public AddOn(AddOn addOn) {
		this.id = addOn.getId();
		this.maxValue = addOn.getMaxValue();
		this.minValue = addOn.getMinValue();
		this.isBaseOnKeyFactor = addOn.isBaseOnKeyFactor();
		this.isCompulsory = addOn.isCompulsory();
		this.basedAmount = addOn.getBasedAmount();
		this.sumInsuredPerUnit = addOn.getSumInsuredPerUnit();
		this.premiumRateType = addOn.getPremiumRateType();
		this.productBaseType = addOn.getProductBaseType();
		this.productContent = addOn.getProductContent();
		this.recorder = addOn.getRecorder();
		this.version = addOn.getVersion();
		for (KeyFactor kf : addOn.getKeyFactorList()) {
			getKeyFactorList().add(new KeyFactor(kf));
		}
	}

	public AddOn(AddOn addOn, double premiumRate) {
		this.id = addOn.getId();
		this.maxValue = addOn.getMaxValue();
		this.minValue = addOn.getMinValue();
		this.isBaseOnKeyFactor = addOn.isBaseOnKeyFactor();
		this.isCompulsory = addOn.isCompulsory();
		this.basedAmount = addOn.getBasedAmount();
		this.premiumRateType = addOn.getPremiumRateType();
		this.productBaseType = addOn.getProductBaseType();
		this.productContent = addOn.getProductContent();
	}

	public AddOn(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public boolean isInclude() {
		return include;
	}

	public void setInclude(boolean include) {
		this.include = include;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	public double getMinValue() {
		return minValue;
	}

	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	public boolean isBaseOnKeyFactor() {
		return isBaseOnKeyFactor;
	}

	public void setBaseOnKeyFactor(boolean isBaseOnKeyFactor) {
		this.isBaseOnKeyFactor = isBaseOnKeyFactor;
	}

	public boolean isCompulsory() {
		return isCompulsory;
	}

	public void setCompulsory(boolean isCompulsory) {
		this.isCompulsory = isCompulsory;
	}

	public PremiumRateType getPremiumRateType() {
		return premiumRateType;
	}

	public void setPremiumRateType(PremiumRateType premiumRateType) {
		this.premiumRateType = premiumRateType;
	}

	public ProductBaseType getProductBaseType() {
		return productBaseType;
	}

	public void setProductBaseType(ProductBaseType productBaseType) {
		this.productBaseType = productBaseType;
	}

	public ProductContent getProductContent() {
		return productContent;
	}

	public void setProductContent(ProductContent productContent) {
		this.productContent = productContent;
	}

	public List<KeyFactor> getKeyFactorList() {
		if (keyFactorList == null) {
			keyFactorList = new ArrayList<KeyFactor>();
		}
		return keyFactorList;
	}

	public void setKeyFactorList(List<KeyFactor> keyFactorList) {
		this.keyFactorList = keyFactorList;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public double getBasedAmount() {
		return basedAmount;
	}

	public void setBasedAmount(double basedAmount) {
		this.basedAmount = basedAmount;
	}

	public double getSumInsuredPerUnit() {
		return sumInsuredPerUnit;
	}

	public void setSumInsuredPerUnit(double sumInsuredPerUnit) {
		this.sumInsuredPerUnit = sumInsuredPerUnit;
	}

	/*************************
	 * System Generated Method
	 *************************/

	public String getName() {
		String result = "";
		if (productContent != null) {
			result = productContent.getName();
		}
		return result;
	}

	public String getCode() {
		String result = "";
		if (productContent != null) {
			result = productContent.getCode();
		}
		return result;
	}

	public boolean isNeedInputType() {
		boolean result = false;
		if (PremiumRateType.BASED_ON_OWN_SUMINSURED.equals(premiumRateType)) {
			result = true;
		} else if (PremiumRateType.PER_UNIT.equals(premiumRateType)) {
			result = true;
		}
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(basedAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (include ? 1231 : 1237);
		result = prime * result + (isBaseOnKeyFactor ? 1231 : 1237);
		result = prime * result + (isCompulsory ? 1231 : 1237);
		result = prime * result + ((keyFactorList == null) ? 0 : keyFactorList.hashCode());
		temp = Double.doubleToLongBits(maxValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(minValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((premiumRateType == null) ? 0 : premiumRateType.hashCode());
		result = prime * result + ((productBaseType == null) ? 0 : productBaseType.hashCode());
		result = prime * result + ((productContent == null) ? 0 : productContent.hashCode());
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		AddOn other = (AddOn) obj;
		if (Double.doubleToLongBits(basedAmount) != Double.doubleToLongBits(other.basedAmount))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (include != other.include)
			return false;
		if (isBaseOnKeyFactor != other.isBaseOnKeyFactor)
			return false;
		if (isCompulsory != other.isCompulsory)
			return false;
		if (keyFactorList == null) {
			if (other.keyFactorList != null)
				return false;
		} else if (!keyFactorList.equals(other.keyFactorList))
			return false;
		if (Double.doubleToLongBits(maxValue) != Double.doubleToLongBits(other.maxValue))
			return false;
		if (Double.doubleToLongBits(minValue) != Double.doubleToLongBits(other.minValue))
			return false;
		if (premiumRateType != other.premiumRateType)
			return false;
		if (productBaseType != other.productBaseType)
			return false;
		if (productContent == null) {
			if (other.productContent != null)
				return false;
		} else if (!productContent.equals(other.productContent))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}