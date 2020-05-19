package org.tat.fni.api.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;



@Entity
@Table(name = TableName.PERSONPRODUCTHISTORY)
@TableGenerator(name = "PERSONPRODUCTHISTORY_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "PERSONPRODUCTHISTORY_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class PersonProductHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "PERSONPRODUCTHISTORY_GEN")
	private String id;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTID", referencedColumnName = "ID")
	private Product product;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HISTORYRECORDID", referencedColumnName = "ID")
	private MedicalPersonHistoryRecord historyRecord;

	@Embedded
	private UserRecorder recorder;

	@Version
	private int version;

	public PersonProductHistory() {
	}

	public PersonProductHistory(Product product) {
		this.product = product;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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

	public MedicalPersonHistoryRecord getHistoryRecord() {
		return historyRecord;
	}

	public void setHistoryRecord(MedicalPersonHistoryRecord historyRecord) {
		this.historyRecord = historyRecord;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((historyRecord == null) ? 0 : historyRecord.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
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
		PersonProductHistory other = (PersonProductHistory) obj;
		if (historyRecord == null) {
			if (other.historyRecord != null)
				return false;
		} else if (!historyRecord.equals(other.historyRecord))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
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
