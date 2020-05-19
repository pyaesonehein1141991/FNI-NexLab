package org.tat.fni.api.common;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.tat.fni.api.domain.Branch;



@Entity
@Table(name = "CUSTOM_ID_GEN")
@NamedQueries(value = { @NamedQuery(name = "IDGen.updateIDGen", query = "SELECT r FROM IDGen r WHERE r.generateItem = :generateItem"),
		@NamedQuery(name = "IDGen.findById", query = "SELECT r FROM IDGen r WHERE r.generateItem = :generateItem") })
public class IDGen implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String generateItem;
	private int maxValue;
	private String prefix;
	private String suffix;
	private String description;
	private int length;
	private boolean isDateBased;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCHID", referencedColumnName = "ID")
	private Branch branch;

	@ElementCollection
	@CollectionTable(name = "CUSTOMEIDGEN_PRODUCT_LINK", joinColumns = @JoinColumn(name = "CUSTOMEIDGENID"))
	@Column(name = "PRODUCTID")
	private List<String> productList;

	@Version
	private int version;

	public IDGen() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IDGen(String id, String generateItem, int maxValue, String prefix, int length, boolean isDateBased, Branch branch) {
		this.id = id;
		this.generateItem = generateItem;
		this.maxValue = maxValue;
		this.prefix = prefix;
		this.length = length;
		this.isDateBased = true;
		this.branch = branch;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGenerateItem() {
		return generateItem;
	}

	public void setGenerateItem(String generateItem) {
		this.generateItem = generateItem;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public boolean isDateBased() {
		return isDateBased;
	}

	public void setDateBased(boolean isDateBased) {
		this.isDateBased = isDateBased;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public List<String> getProductList() {
		return productList;
	}

	public void setProductList(List<String> productList) {
		this.productList = productList;
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
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((generateItem == null) ? 0 : generateItem.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isDateBased ? 1231 : 1237);
		result = prime * result + length;
		result = prime * result + maxValue;
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
		result = prime * result + ((productList == null) ? 0 : productList.hashCode());
		result = prime * result + ((suffix == null) ? 0 : suffix.hashCode());
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
		IDGen other = (IDGen) obj;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (generateItem == null) {
			if (other.generateItem != null)
				return false;
		} else if (!generateItem.equals(other.generateItem))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isDateBased != other.isDateBased)
			return false;
		if (length != other.length)
			return false;
		if (maxValue != other.maxValue)
			return false;
		if (prefix == null) {
			if (other.prefix != null)
				return false;
		} else if (!prefix.equals(other.prefix))
			return false;
		if (productList == null) {
			if (other.productList != null)
				return false;
		} else if (!productList.equals(other.productList))
			return false;
		if (suffix == null) {
			if (other.suffix != null)
				return false;
		} else if (!suffix.equals(other.suffix))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}