/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.tat.fni.api.common;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.tat.fni.api.common.emumdata.KeyFactorType;



@Entity
@Table(name = TableName.KEYFACTOR)
@TableGenerator(name = "KEYFACTOR_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "KEYFACTOR_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "KeyFactor.findAll", query = "SELECT k FROM KeyFactor k ORDER BY k.value ASC"),
		@NamedQuery(name = "KeyFactor.findById", query = "SELECT k FROM KeyFactor k WHERE k.id = :id") })
@EntityListeners(IDInterceptor.class)
public class KeyFactor implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "KEYFACTOR_GEN")
	private String id;

	private String value;
	@Enumerated(value = EnumType.STRING)
	private KeyFactorType keyFactorType;
	@Version
	private int version;
	@Embedded
	private UserRecorder recorder;

	public KeyFactor() {
	}

	public KeyFactor(KeyFactor keyFactor) {
		this.id = keyFactor.getId();
		this.value = keyFactor.getValue();
		this.keyFactorType = keyFactor.getKeyFactorType();
		this.recorder = keyFactor.getRecorder();
		this.version = keyFactor.getVersion();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public KeyFactorType getKeyFactorType() {
		return this.keyFactorType;
	}

	public void setKeyFactorType(KeyFactorType keyFactorType) {
		this.keyFactorType = keyFactorType;
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

	public String getEnumValue() {
		return value.substring(value.lastIndexOf(".") + 1);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((keyFactorType == null) ? 0 : keyFactorType.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		KeyFactor other = (KeyFactor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (keyFactorType != other.keyFactorType)
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}