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
import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.PermanentAddress;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;
import org.tat.fni.api.common.emumdata.ContentInfo;


@Entity
@Table(name = TableName.ORGANIZATION)
@TableGenerator(name = "ORGANIZATION_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "ORGANIZATION_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "Organization.findAll", query = "SELECT o FROM Organization o ORDER BY o.name ASC"),
		@NamedQuery(name = "Organization.findById", query = "SELECT o FROM Organization o WHERE o.id = :id") })
@EntityListeners(IDInterceptor.class)
public class Organization implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ORGANIZATION_GEN")
	private String id;
	@Column(name = "CODE_NO")
	private String codeNo;
	@Column(name = "NAME")
	private String name;
	@Column(name = "REG_NO")
	private String regNo;
	@Column(name = "OWNER_NAME")
	private String OwnerName;
	private int activePolicy;
	@Temporal(TemporalType.TIMESTAMP)
	private Date activedDate;

	@Embedded
	@AttributeOverride(name = "permanentAddress", column = @Column(name = "ADDRESS"))
	@AssociationOverride(name = "township", joinColumns = @JoinColumn(name = "TOWNSHIP_ID"))
	private PermanentAddress address;

	@Embedded
	private ContentInfo contentInfo;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCHID", referencedColumnName = "ID")
	private Branch branch;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public Organization() {
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
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PermanentAddress getAddress() {
		if (this.address == null) {
			this.address = new PermanentAddress();
		}
		return this.address;
	}

	public void setAddress(PermanentAddress address) {
		this.address = address;
	}

	public ContentInfo getContentInfo() {
		if (this.contentInfo == null) {
			this.contentInfo = new ContentInfo();
		}
		return this.contentInfo;
	}

	public void setContentInfo(ContentInfo contentInfo) {
		this.contentInfo = contentInfo;
	}

	public String getCodeNo() {
		return codeNo;
	}

	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getOwnerName() {
		return OwnerName;
	}

	public String getOwnerNameForView() {
		if (OwnerName == null || OwnerName.isEmpty())
			return "-";
		return OwnerName;
	}

	public void setOwnerName(String ownerName) {
		OwnerName = ownerName;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Date getActivedDate() {
		return activedDate;
	}

	public void setActivedDate(Date activedDate) {
		this.activedDate = activedDate;
	}

	public String getFullAddress() {
		String fullAddress = "";
		if (address != null) {
			String townShip = address.getTownship() == null ? "" : address.getTownship().getFullTownShip();
			fullAddress = address.getPermanentAddress() + ", " + townShip;
		}
		return fullAddress;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((OwnerName == null) ? 0 : OwnerName.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((codeNo == null) ? 0 : codeNo.hashCode());
		result = prime * result + ((contentInfo == null) ? 0 : contentInfo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((regNo == null) ? 0 : regNo.hashCode());
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
		Organization other = (Organization) obj;
		if (OwnerName == null) {
			if (other.OwnerName != null)
				return false;
		} else if (!OwnerName.equals(other.OwnerName))
			return false;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (codeNo == null) {
			if (other.codeNo != null)
				return false;
		} else if (!codeNo.equals(other.codeNo))
			return false;
		if (contentInfo == null) {
			if (other.contentInfo != null)
				return false;
		} else if (!contentInfo.equals(other.contentInfo))
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
		if (regNo == null) {
			if (other.regNo != null)
				return false;
		} else if (!regNo.equals(other.regNo))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	public int getActivePolicy() {
		return activePolicy;
	}

	public void setActivePolicy(int activePolicy) {
		this.activePolicy = activePolicy;
	}
}