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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.PermanentAddress;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;
import org.tat.fni.api.common.emumdata.ContentInfo;



@Entity
@Table(name = TableName.COINSURANCE_COMPANY)
@TableGenerator(name = "COINSURANCECOMPANY_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "COINSURANCECOMPANY_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "CoinsuranceCompany.findAll", query = "SELECT c FROM CoinsuranceCompany c order by c.name ASC "),
		@NamedQuery(name = "CoinsuranceCompany.findById", query = "SELECT c FROM CoinsuranceCompany c WHERE c.id = :id"),
		@NamedQuery(name = "CoinsuranceCompany.findByGroup", query = "SELECT g.coinsuranceCompany FROM CoinsuredProductGroup g WHERE g.productGroup.id = :groupId") })
@EntityListeners(IDInterceptor.class)
public class CoinsuranceCompany implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "COINSURANCECOMPANY_GEN")
	private String id;

	private String name;
	private String description;

	@Embedded
	private PermanentAddress address;

	@Embedded
	private ContentInfo contentInfo;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "coinsuranceCompany", orphanRemoval = true)
	private List<CoinsuredProductGroup> coinsuredProductGroups;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public CoinsuranceCompany() {
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

	public PermanentAddress getAddress() {
		if (this.address == null) {
			this.address = new PermanentAddress();
		}
		return address;
	}

	public void setAddress(PermanentAddress address) {
		this.address = address;
	}

	public ContentInfo getContentInfo() {
		if (contentInfo == null) {
			contentInfo = new ContentInfo();
		}
		return contentInfo;
	}

	public void setContentInfo(ContentInfo contentInfo) {
		this.contentInfo = contentInfo;
	}

	public List<CoinsuredProductGroup> getCoinsuredProductGroups() {
		return coinsuredProductGroups;
	}

	public void setCoinsuredProductGroups(List<CoinsuredProductGroup> coinsuredProductGroups) {
		this.coinsuredProductGroups = coinsuredProductGroups;
	}

	public void addConinsuranceProductGroup(CoinsuredProductGroup coinsuredProductGroup) {
		if (this.coinsuredProductGroups == null) {
			coinsuredProductGroups = new ArrayList<CoinsuredProductGroup>();
		}
		coinsuredProductGroup.setCoinsuranceCompany(this);
		coinsuredProductGroups.add(coinsuredProductGroup);
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
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((contentInfo == null) ? 0 : contentInfo.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
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
		CoinsuranceCompany other = (CoinsuranceCompany) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (contentInfo == null) {
			if (other.contentInfo != null)
				return false;
		} else if (!contentInfo.equals(other.contentInfo))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
