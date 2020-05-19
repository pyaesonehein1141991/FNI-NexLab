package org.tat.fni.api.domain;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.NcbRate;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;
import org.tat.fni.api.common.emumdata.NcbYear;
import org.tat.fni.api.common.emumdata.ProductGroupType;

@Entity
@Table(name = TableName.PRODUCTGROUP)
@TableGenerator(name = "PRODUCTGROUP_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "PRODUCTGROUP_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "ProductGroup.findAll", query = "SELECT p FROM ProductGroup p ORDER by p.name ASC"),
		@NamedQuery(name = "ProductGroup.findByGroupType", query = "SELECT p FROM ProductGroup p WHERE p.groupType = :groupType ORDER by p.name ASC"),
		@NamedQuery(name = "ProductGroup.findById", query = "SELECT p FROM ProductGroup p WHERE p.id = :id") })
@EntityListeners(IDInterceptor.class)
public class ProductGroup {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "PRODUCTGROUP_GEN")
	private String id;

	private String name;
	private String description;
	private String policyNoPrefix;
	private String proposalNoPrefix;
	private double fleetDiscount;
	private double underSession13;
	private double underSession25;
	private double maxSumInsured;

	@Column(name = "ACCOUNTNAME")
	private String accountCode;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "GROUPTYPE")
	private ProductGroupType groupType;

	@ElementCollection
	@CollectionTable(name = "PRODUCTGROUP_NCBRATE_LINK", joinColumns = @JoinColumn(name = "PRODUCTGROUPID", referencedColumnName = "ID"))
	private List<NcbRate> ncbRates;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public ProductGroup() {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPolicyNoPrefix() {
		return policyNoPrefix;
	}

	public void setPolicyNoPrefix(String policyNoPrefix) {
		this.policyNoPrefix = policyNoPrefix;
	}

	public String getProposalNoPrefix() {
		return proposalNoPrefix;
	}

	public void setProposalNoPrefix(String proposalNoPrefix) {
		this.proposalNoPrefix = proposalNoPrefix;
	}

	public double getUnderSession13() {
		return underSession13;
	}

	public void setUnderSession13(double underSession13) {
		this.underSession13 = underSession13;
	}

	public double getUnderSession25() {
		return underSession25;
	}

	public void setUnderSession25(double underSession25) {
		this.underSession25 = underSession25;
	}

	public double getMaxSumInsured() {
		return maxSumInsured;
	}

	public void setMaxSumInsured(double maxSumInsured) {
		this.maxSumInsured = maxSumInsured;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public ProductGroupType getGroupType() {
		return groupType;
	}

	public void setGroupType(ProductGroupType groupType) {
		this.groupType = groupType;
	}

	public List<NcbRate> getNcbRates() {
		return ncbRates;
	}

	public void setNcbRates(List<NcbRate> ncbRates) {
		this.ncbRates = ncbRates;
	}

	public double getFleetDiscount() {
		return fleetDiscount;
	}

	public void setFleetDiscount(double fleetDiscount) {
		this.fleetDiscount = fleetDiscount;
	}

	public float getNCBPercentage(NcbYear ncbYear) {
		float percentage = 0.0f;
		if (ncbRates != null && !ncbRates.isEmpty()) {
			switch (ncbYear) {
				case FIRST_YEAR: {
					for (NcbRate rate : ncbRates) {
						if (NcbYear.FIRST_YEAR.equals(rate.getNcbYear())) {
							percentage = rate.getNcbPercentage();
						}
					}
				}
					break;
				case SECOND_YEAR: {
					for (NcbRate rate : ncbRates) {
						if (NcbYear.SECOND_YEAR.equals(rate.getNcbYear())) {
							percentage = rate.getNcbPercentage();
						}
					}
				}
					break;
				case THIRD_YEAR: {
					for (NcbRate rate : ncbRates) {
						if (NcbYear.THIRD_YEAR.equals(rate.getNcbYear())) {
							percentage = rate.getNcbPercentage();
						}
					}
				}
					break;
				case ABOVE_THIRD_YEAR: {
					for (NcbRate rate : ncbRates) {
						if (NcbYear.ABOVE_THIRD_YEAR.equals(rate.getNcbYear())) {
							percentage = rate.getNcbPercentage();
						}
					}
				}
					break;
				case FIXED: {
					for (NcbRate rate : ncbRates) {
						if (NcbYear.FIXED.equals(rate.getNcbYear())) {
							percentage = rate.getNcbPercentage();
						}
					}
				}
					break;
			}
		}
		return percentage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountCode == null) ? 0 : accountCode.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		long temp;
		temp = Double.doubleToLongBits(fleetDiscount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((groupType == null) ? 0 : groupType.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		temp = Double.doubleToLongBits(maxSumInsured);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((ncbRates == null) ? 0 : ncbRates.hashCode());
		result = prime * result + ((policyNoPrefix == null) ? 0 : policyNoPrefix.hashCode());
		result = prime * result + ((proposalNoPrefix == null) ? 0 : proposalNoPrefix.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		temp = Double.doubleToLongBits(underSession13);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(underSession25);
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
		ProductGroup other = (ProductGroup) obj;
		if (accountCode == null) {
			if (other.accountCode != null)
				return false;
		} else if (!accountCode.equals(other.accountCode))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (Double.doubleToLongBits(fleetDiscount) != Double.doubleToLongBits(other.fleetDiscount))
			return false;
		if (groupType != other.groupType)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(maxSumInsured) != Double.doubleToLongBits(other.maxSumInsured))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (ncbRates == null) {
			if (other.ncbRates != null)
				return false;
		} else if (!ncbRates.equals(other.ncbRates))
			return false;
		if (policyNoPrefix == null) {
			if (other.policyNoPrefix != null)
				return false;
		} else if (!policyNoPrefix.equals(other.policyNoPrefix))
			return false;
		if (proposalNoPrefix == null) {
			if (other.proposalNoPrefix != null)
				return false;
		} else if (!proposalNoPrefix.equals(other.proposalNoPrefix))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (Double.doubleToLongBits(underSession13) != Double.doubleToLongBits(other.underSession13))
			return false;
		if (Double.doubleToLongBits(underSession25) != Double.doubleToLongBits(other.underSession25))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
