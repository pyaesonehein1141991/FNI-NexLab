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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.KeyFactor;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;
import org.tat.fni.api.domain.addon.AddOn;

@Entity
@Table(name = TableName.PRODUCT_PREMIUMRATE)
@TableGenerator(name = "PRODUCTPREMIUMRATE_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "PRODUCTPREMIUMRATE_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "ProductPremiumRate.findAll", query = "SELECT p FROM ProductPremiumRate p "),
		@NamedQuery(name = "ProductPremiumRate.findById", query = "SELECT p FROM ProductPremiumRate p WHERE p.id = :id"),
		@NamedQuery(name = "ProductPremiumRate.findByProductId", query = "SELECT p FROM ProductPremiumRate p WHERE p.product.id = :productId"),
		@NamedQuery(name = "ProductPremiumRate.findByAddOnId", query = "SELECT p FROM ProductPremiumRate p WHERE p.addOn.id = :addonId"),
		@NamedQuery(name = "ProductPremiumRate.deleteById", query = "DELETE FROM ProductPremiumRate p WHERE p.id = :id") })
@EntityListeners(IDInterceptor.class)
public class ProductPremiumRate implements Serializable, Comparable<ProductPremiumRate> {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "PRODUCTPREMIUMRATE_GEN")
	private String id;

	private double premiumRate;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "PRODUCTPREMIUMRATEID", referencedColumnName = "ID")
	private List<ProductPremiumRateKeyFactor> premiumRateKeyFactor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTID", referencedColumnName = "ID")
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADDONID", referencedColumnName = "ID")
	private AddOn addOn;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public ProductPremiumRate() {
	}

	public ProductPremiumRate(double premiumRate, Product product) {
		this.premiumRate = premiumRate;
		this.product = product;
	}

	public ProductPremiumRate(double premiumRate, Product product, AddOn addon) {
		this.premiumRate = premiumRate;

		this.product = product;
		this.addOn = addon;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public double getPremiumRate() {
		return this.premiumRate;
	}

	public void setPremiumRate(double premiumRate) {
		this.premiumRate = premiumRate;
	}

	public List<ProductPremiumRateKeyFactor> getPremiumRateKeyFactor() {
		return this.premiumRateKeyFactor;
	}

	public void setPremiumRateKeyFactor(List<ProductPremiumRateKeyFactor> premiumRateKeyFactor) {
		this.premiumRateKeyFactor = premiumRateKeyFactor;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public AddOn getAddon() {
		return addOn;
	}

	public void setAddon(AddOn addon) {
		this.addOn = addon;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public List<KeyFactor> getKeyFactorList() {
		List<KeyFactor> kfList = new ArrayList<KeyFactor>();
		for (ProductPremiumRateKeyFactor pprkf : premiumRateKeyFactor) {
			kfList.add(pprkf.getKeyFactor());
		}
		return kfList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		long temp;
		temp = Double.doubleToLongBits(premiumRate);
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
		ProductPremiumRate other = (ProductPremiumRate) obj;
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
		if (Double.doubleToLongBits(premiumRate) != Double.doubleToLongBits(other.premiumRate))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	@Override
	public int compareTo(ProductPremiumRate productPremiumRate) {
		if (this.premiumRate > productPremiumRate.getPremiumRate()) {
			return 1;
		} else if (this.premiumRate < productPremiumRate.getPremiumRate()) {
			return -1;
		} else {
			return 0;
		}
	}
}