package org.tat.fni.api.domain;

import java.io.Serializable;

import javax.persistence.Column;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;



/**
 * Entity implementation class for Entity: CoinsuranceCompanyProductGroupLink
 *
 */
@Entity
@Table(name = TableName.COINSURANCE_PRODUCT_GROUP)
@TableGenerator(name = "COINSUREDPRODUCTGROUP_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "COINSUREDPRODUCTGROUP_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "CoinsuredProductGroup.findByGroup", query = "SELECT g FROM CoinsuredProductGroup g WHERE g.productGroup.id = :groupId") })
@EntityListeners(IDInterceptor.class)
public class CoinsuredProductGroup implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "COINSUREDPRODUCTGROUP_GEN")
	private String id;

	@Column(name = "PERCENTAGE")
	private double precentage;

	private double commissionPercent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COINSURANCECOMPANYID", referencedColumnName = "ID")
	private CoinsuranceCompany coinsuranceCompany;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTGROUPID", referencedColumnName = "ID")
	private ProductGroup productGroup;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public CoinsuredProductGroup() {
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

	public double getPrecentage() {
		return precentage;
	}

	public void setPrecentage(double precentage) {
		this.precentage = precentage;
	}

	public double getCommissionPercent() {
		return commissionPercent;
	}

	public void setCommissionPercent(double commissionPercent) {
		this.commissionPercent = commissionPercent;
	}

	public CoinsuranceCompany getCoinsuranceCompany() {
		return coinsuranceCompany;
	}

	public void setCoinsuranceCompany(CoinsuranceCompany coinsuranceCompany) {
		this.coinsuranceCompany = coinsuranceCompany;
	}

	public ProductGroup getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(ProductGroup productGroup) {
		this.productGroup = productGroup;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coinsuranceCompany == null) ? 0 : coinsuranceCompany.hashCode());
		long temp;
		temp = Double.doubleToLongBits(commissionPercent);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		temp = Double.doubleToLongBits(precentage);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((productGroup == null) ? 0 : productGroup.hashCode());
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
		CoinsuredProductGroup other = (CoinsuredProductGroup) obj;
		if (coinsuranceCompany == null) {
			if (other.coinsuranceCompany != null)
				return false;
		} else if (!coinsuranceCompany.equals(other.coinsuranceCompany))
			return false;
		if (Double.doubleToLongBits(commissionPercent) != Double.doubleToLongBits(other.commissionPercent))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(precentage) != Double.doubleToLongBits(other.precentage))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (productGroup == null) {
			if (other.productGroup != null)
				return false;
		} else if (!productGroup.equals(other.productGroup))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
