package org.tat.fni.api.domain;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;
import org.tat.fni.api.common.emumdata.SchoolLevelType;
import org.tat.fni.api.common.emumdata.SchoolType;

@Entity
@Table(name = TableName.SCHOOL)
@EntityListeners(IDInterceptor.class)
@TableGenerator(name = "SCHOOL_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "SCHOOL_GEN", allocationSize = 10)
public class School {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SCHOOL_GEN")
	private String id;
	private String name;
	private String address;
	private String phoneNo;
	private String schoolCodeNo;

	@Enumerated(EnumType.STRING)
	private SchoolType schoolType;

	@Enumerated(EnumType.STRING)
	private SchoolLevelType schoolLevelType;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TOWNSHIPID", referencedColumnName = "ID")
	private Township township;

	private int version;

	@Embedded
	private UserRecorder userRecorder;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Township getTownship() {
		return township;
	}

	public void setTownship(Township township) {
		this.township = township;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public UserRecorder getUserRecorder() {
		return userRecorder;
	}

	public void setUserRecorder(UserRecorder userRecorder) {
		this.userRecorder = userRecorder;
	}

	public SchoolType getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(SchoolType schoolType) {
		this.schoolType = schoolType;
	}

	public SchoolLevelType getSchoolLevelType() {
		return schoolLevelType;
	}

	public void setSchoolLevelType(SchoolLevelType schoolLevelType) {
		this.schoolLevelType = schoolLevelType;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getSchoolCodeNo() {
		return schoolCodeNo;
	}

	public void setSchoolCodeNo(String schoolCodeNo) {
		this.schoolCodeNo = schoolCodeNo;
	}

	public String getFullAddress() {
		String result = "";
		if (address != null && !address.isEmpty()) {
			result = result + address;
		}
		if (township != null) {
			result = result + ", " + township.getName();
		}
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((userRecorder == null) ? 0 : userRecorder.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phoneNo == null) ? 0 : phoneNo.hashCode());
		result = prime * result + ((schoolCodeNo == null) ? 0 : schoolCodeNo.hashCode());
		result = prime * result + ((schoolLevelType == null) ? 0 : schoolLevelType.hashCode());
		result = prime * result + ((schoolType == null) ? 0 : schoolType.hashCode());
		result = prime * result + ((township == null) ? 0 : township.hashCode());
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
		School other = (School) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (userRecorder == null) {
			if (other.userRecorder != null)
				return false;
		} else if (!userRecorder.equals(other.userRecorder))
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
		if (phoneNo == null) {
			if (other.phoneNo != null)
				return false;
		} else if (!phoneNo.equals(other.phoneNo))
			return false;
		if (schoolCodeNo == null) {
			if (other.schoolCodeNo != null)
				return false;
		} else if (!schoolCodeNo.equals(other.schoolCodeNo))
			return false;
		if (schoolLevelType != other.schoolLevelType)
			return false;
		if (schoolType != other.schoolType)
			return false;
		if (township == null) {
			if (other.township != null)
				return false;
		} else if (!township.equals(other.township))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
