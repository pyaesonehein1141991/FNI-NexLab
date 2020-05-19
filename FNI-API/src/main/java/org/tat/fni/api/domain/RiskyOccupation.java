package org.tat.fni.api.domain;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;


@Entity
@Table(name=TableName.RISKYOCCUPATION)
@TableGenerator(name="RISKYOCCUPATION_GEN",table="ID_GEN",pkColumnName="GEN_NAME",valueColumnName="GEN_VAL",pkColumnValue="RISKYOCCUPATION_GEN",allocationSize=10)
@NamedQueries(value = { @NamedQuery(name = "RiskyOccupation.findAll", query = "SELECT r FROM RiskyOccupation r ORDER BY r.name ASC"),
		@NamedQuery(name = "RiskyOccupation.findById", query = "SELECT r FROM RiskyOccupation r WHERE r.id = :id") })
@EntityListeners(IDInterceptor.class)
public class RiskyOccupation implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "RISKYOCCUPATION_GEN")
	private String id;
	private String name;
	private String mName;
	private String description;	
	private int extraRate;
	
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	
	public RiskyOccupation() {
	}


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


	public String getmName() {
		return mName;
	}


	public void setmName(String mName) {
		this.mName = mName;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getExtraRate() {
		return extraRate;
	}


	public void setExtraRate(int extraRate) {
		this.extraRate = extraRate;
	}


	public UserRecorder getRecorder() {
		return recorder;
	}


	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
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
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + extraRate;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mName == null) ? 0 : mName.hashCode());
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
		RiskyOccupation other = (RiskyOccupation) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (extraRate != other.extraRate)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mName == null) {
			if (other.mName != null)
				return false;
		} else if (!mName.equals(other.mName))
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
