/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.tat.fni.api.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.Version;

import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.KeyFactor;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;
import org.tat.fni.api.common.emumdata.InsuranceType;
import org.tat.fni.api.common.emumdata.PeriodType;
import org.tat.fni.api.common.emumdata.PremiumRateType;
import org.tat.fni.api.common.emumdata.ProductBaseType;
import org.tat.fni.api.common.emumdata.StampFeeRateType;
import org.tat.fni.api.domain.addon.AddOn;


@Entity
@Table(name = TableName.PRODUCT)
@TableGenerator(name = "PRODUCT_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "PRODUCT_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p ORDER by p.productContent.name ASC"),
		@NamedQuery(name = "Product.findById", query = "SELECT p FROM Product p WHERE p.id = :id"),
		@NamedQuery(name = "Product.findByInsuranceType", query = "SELECT p FROM Product p WHERE p.insuranceType = :insuranceType"),
		@NamedQuery(name = "Product.findProductByCurrencyType", query = "SELECT p FROM Product p WHERE p.insuranceType = :insuranceType AND p.currency = :currency ") })
@EntityListeners(IDInterceptor.class)
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "PRODUCT_GEN")
	private String id;
	private int maxTerm;
	private int minTerm;
	private int standardExcess;
	private int maxAge;
	private int minAge;
	private int maxHospDays;

	@Column(name = "MAXIMUMVALUE")
	private double maxValue;
	@Column(name = "MINIMUMVALUE")
	private double minValue;
	private double basedAmount;
	private float firstCommission;
	private float renewalCommission;
	private boolean isBaseOnKeyFactor;
	private String multiCurPrefix;
	private double stampFee;
	private double stampFeeBasedAmount;
	private double sumInsuredPerUnit;

	@Enumerated(EnumType.STRING)
	private PremiumRateType premiumRateType;

	@Enumerated(EnumType.STRING)
	private StampFeeRateType stampFeeRateType;

	@Enumerated(EnumType.STRING)
	private InsuranceType insuranceType;

	@Enumerated(EnumType.STRING)
	private ProductBaseType productBaseType;

	@Enumerated(EnumType.STRING)
	private PeriodType maxTermType;

	@Enumerated(EnumType.STRING)
	private PeriodType minTermType;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTCONTENTID", referencedColumnName = "ID")
	private ProductContent productContent;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTGROUPID", referencedColumnName = "ID")
	private ProductGroup productGroup;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "PRODUCT_PAYMENTTYPE_LINK", joinColumns = { @JoinColumn(name = "PRODUCTID", referencedColumnName = "ID") }, inverseJoinColumns = {
			@JoinColumn(name = "PAYMENTTYPEID", referencedColumnName = "ID") })
	private List<PaymentType> paymentTypeList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "PRODUCTID", referencedColumnName = "ID")
	private List<AddOn> addOnList;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "PRODUCT_KEYFACTOR_LINK", joinColumns = { @JoinColumn(name = "PRODUCTID", referencedColumnName = "ID") }, inverseJoinColumns = {
			@JoinColumn(name = "KEYFACTORID", referencedColumnName = "ID") })
	private List<KeyFactor> keyFactorList;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENCYID", referencedColumnName = "ID")
	private Currency currency;

	@Embedded
	private UserRecorder recorder;

	@Version
	private int version;

	public Product() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isBaseOnKeyFactor() {
		return isBaseOnKeyFactor;
	}

	public void setBaseOnKeyFactor(boolean isBaseOnKeyFactor) {
		this.isBaseOnKeyFactor = isBaseOnKeyFactor;
	}

	public int getStandardExcess() {
		return this.standardExcess;
	}

	public void setStandardExcess(int standardExcess) {
		this.standardExcess = standardExcess;
	}

	public float getFirstCommission() {
		return firstCommission;
	}

	public void setFirstCommission(float firstCommission) {
		this.firstCommission = firstCommission;
	}

	public void setRenewalCommission(float renewalCommission) {
		this.renewalCommission = renewalCommission;
	}

	public float getRenewalCommission() {
		return renewalCommission;
	}

	public double getBasedAmount() {
		return basedAmount;
	}

	public void setBasedAmount(double basedAmount) {
		this.basedAmount = basedAmount;
	}

	public int getMaxTerm() {
		return maxTerm;
	}

	public void setMaxTerm(int maxTerm) {
		this.maxTerm = maxTerm;
	}

	public int getMinTerm() {
		return minTerm;
	}

	public void setMinTerm(int minTerm) {
		this.minTerm = minTerm;
	}

	public InsuranceType getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(InsuranceType insuranceType) {
		this.insuranceType = insuranceType;
	}

	public PremiumRateType getPremiumRateType() {
		return premiumRateType;
	}

	public void setPremiumRateType(PremiumRateType premiumRateType) {
		this.premiumRateType = premiumRateType;
	}

	public StampFeeRateType getStampFeeRateType() {
		return stampFeeRateType;
	}

	public void setStampFeeRateType(StampFeeRateType stampFeeRateType) {
		this.stampFeeRateType = stampFeeRateType;
	}

	public ProductGroup getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(ProductGroup productGroup) {
		this.productGroup = productGroup;
	}

	public List<PaymentType> getPaymentTypeList() {
		if (paymentTypeList == null) {
			paymentTypeList = new ArrayList<PaymentType>();
		}
		return paymentTypeList;
	}

	public void setPaymentTypeList(List<PaymentType> paymentTypeList) {
		this.paymentTypeList = paymentTypeList;
	}

	public List<AddOn> getAddOnList() {
		if (addOnList == null) {
			addOnList = new ArrayList<AddOn>();
		}
		return addOnList;
	}

	public void setAddOnList(List<AddOn> addOnList) {
		this.addOnList = addOnList;
	}

	public void addAddOn(AddOn addOn) {
		getAddOnList().add(addOn);
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

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public String getMultiCurPrefix() {
		return multiCurPrefix;
	}

	public void setMultiCurPrefix(String multiCurPrefix) {
		this.multiCurPrefix = multiCurPrefix;
	}

	public int getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}

	public int getMinAge() {
		return minAge;
	}

	public void setMinAge(int minAge) {
		this.minAge = minAge;
	}

	public int getMaxHospDays() {
		return maxHospDays;
	}

	public void setMaxHospDays(int maxHospDays) {
		this.maxHospDays = maxHospDays;
	}

	public double getStampFee() {
		return stampFee;
	}

	public void setStampFee(double stampFee) {
		this.stampFee = stampFee;
	}

	public double getStampFeeBasedAmount() {
		return stampFeeBasedAmount;
	}

	public void setStampFeeBasedAmount(double stampFeeBasedAmount) {
		this.stampFeeBasedAmount = stampFeeBasedAmount;
	}

	public ProductContent getProductContent() {
		return productContent;
	}

	public void setProductContent(ProductContent productContent) {
		this.productContent = productContent;
	}

	public ProductBaseType getProductBaseType() {
		return productBaseType;
	}

	public void setProductBaseType(ProductBaseType productBaseType) {
		this.productBaseType = productBaseType;
	}

	public PeriodType getMaxTermType() {
		return maxTermType;
	}

	public void setMaxTermType(PeriodType maxTermType) {
		this.maxTermType = maxTermType;
	}

	public PeriodType getMinTermType() {
		return minTermType;
	}

	public void setMinTermType(PeriodType minTermType) {
		this.minTermType = minTermType;
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

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
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
		String name = "";
		if (productContent != null) {
			name = productContent.getName();
		}
		return name;
	}

	public List<AddOn> getSortedAddOnList() {
		Collections.sort(getAddOnList(), new Comparator<AddOn>() {
			public int compare(AddOn o1, AddOn o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		return addOnList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(basedAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + Float.floatToIntBits(firstCommission);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((insuranceType == null) ? 0 : insuranceType.hashCode());
		result = prime * result + (isBaseOnKeyFactor ? 1231 : 1237);
		result = prime * result + maxAge;
		result = prime * result + maxHospDays;
		result = prime * result + maxTerm;
		result = prime * result + ((maxTermType == null) ? 0 : maxTermType.hashCode());
		temp = Double.doubleToLongBits(maxValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + minAge;
		result = prime * result + minTerm;
		result = prime * result + ((minTermType == null) ? 0 : minTermType.hashCode());
		temp = Double.doubleToLongBits(minValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((multiCurPrefix == null) ? 0 : multiCurPrefix.hashCode());
		result = prime * result + ((premiumRateType == null) ? 0 : premiumRateType.hashCode());
		result = prime * result + ((productBaseType == null) ? 0 : productBaseType.hashCode());
		result = prime * result + ((productContent == null) ? 0 : productContent.hashCode());
		result = prime * result + ((productGroup == null) ? 0 : productGroup.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + Float.floatToIntBits(renewalCommission);
		temp = Double.doubleToLongBits(stampFee);
		temp = Double.doubleToLongBits(stampFeeBasedAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((stampFeeRateType == null) ? 0 : stampFeeRateType.hashCode());
		result = prime * result + standardExcess;
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
		Product other = (Product) obj;
		if (Double.doubleToLongBits(basedAmount) != Double.doubleToLongBits(other.basedAmount))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (Float.floatToIntBits(firstCommission) != Float.floatToIntBits(other.firstCommission))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (insuranceType != other.insuranceType)
			return false;
		if (isBaseOnKeyFactor != other.isBaseOnKeyFactor)
			return false;
		if (maxAge != other.maxAge)
			return false;
		if (maxHospDays != other.maxHospDays)
			return false;
		if (maxTerm != other.maxTerm)
			return false;
		if (maxTermType != other.maxTermType)
			return false;
		if (Double.doubleToLongBits(maxValue) != Double.doubleToLongBits(other.maxValue))
			return false;
		if (minAge != other.minAge)
			return false;
		if (minTerm != other.minTerm)
			return false;
		if (minTermType != other.minTermType)
			return false;
		if (Double.doubleToLongBits(minValue) != Double.doubleToLongBits(other.minValue))
			return false;
		if (multiCurPrefix == null) {
			if (other.multiCurPrefix != null)
				return false;
		} else if (!multiCurPrefix.equals(other.multiCurPrefix))
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
		if (productGroup == null) {
			if (other.productGroup != null)
				return false;
		} else if (!productGroup.equals(other.productGroup))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (Float.floatToIntBits(renewalCommission) != Float.floatToIntBits(other.renewalCommission))
			return false;
		if (Double.doubleToLongBits(stampFee) != Double.doubleToLongBits(other.stampFee))
			return false;
		if (Double.doubleToLongBits(stampFeeBasedAmount) != Double.doubleToLongBits(other.stampFeeBasedAmount))
			return false;
		if (stampFeeRateType != other.stampFeeRateType)
			return false;
		if (standardExcess != other.standardExcess)
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}