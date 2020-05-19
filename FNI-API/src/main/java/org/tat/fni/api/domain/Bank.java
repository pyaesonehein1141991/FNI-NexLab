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

import javax.persistence.Column;
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
@Table(name = TableName.BANK)
@TableGenerator(name = "BANK_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "BANK_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "Bank.findAll", query = "SELECT b FROM Bank b ORDER BY b.name ASC"),
		@NamedQuery(name = "Bank.findById", query = "SELECT b FROM Bank b WHERE b.id = :id") })
@EntityListeners(IDInterceptor.class)
public class Bank implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "BANK_GEN")
	private String id;

	private String name;
	private String description;
	private String acode;

	@Column(name = "CSC_BANK_CODE")
	private String cscBankCode;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public Bank() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAcode() {
		return this.acode;
	}

	public void setAcode(String acode) {
		this.acode = acode;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public String getCscBankCode() {
		return cscBankCode;
	}

	public void setCscBankCode(String cscBankCode) {
		this.cscBankCode = cscBankCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acode == null) ? 0 : acode.hashCode());
		result = prime * result + ((cscBankCode == null) ? 0 : cscBankCode.hashCode());
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
		Bank other = (Bank) obj;
		if (acode == null) {
			if (other.acode != null)
				return false;
		} else if (!acode.equals(other.acode))
			return false;
		if (cscBankCode == null) {
			if (other.cscBankCode != null)
				return false;
		} else if (!cscBankCode.equals(other.cscBankCode))
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