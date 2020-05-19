package org.tat.fni.api.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;
import org.tat.fni.api.common.emumdata.CustomerStatus;


@Entity
@Table(name = TableName.CUSTOMERSTATUS)
@TableGenerator(name = "CUSTOMERSTATUS_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "CUSTOMERSTATUS_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "CustomerInfoStatus.findAll", query = "SELECT c FROM CustomerInfoStatus c"),
		@NamedQuery(name = "CustomerInfoStatus.findByCustomerId", query = "SELECT c FROM CustomerInfoStatus c where c.customer.id =:id") })
@Access(value = AccessType.FIELD)
@EntityListeners(IDInterceptor.class)
public class CustomerInfoStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "CUSTOMERSTATUS_GEN")
	private String id;
	@Enumerated(EnumType.STRING)
	private CustomerStatus statusName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMERID", referencedColumnName = "ID")
	private Customer customer;
	@Version
	private int version;

	@Embedded
	private UserRecorder recorder;

	public CustomerInfoStatus() {
	}

	public CustomerInfoStatus(String id, CustomerStatus statusName, Customer customer, int version) {
		this.id = id;
		this.statusName = statusName;
		this.customer = customer;
		this.version = version;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public CustomerStatus getStatusName() {
		return statusName;
	}

	public void setStatusName(CustomerStatus statusName) {
		this.statusName = statusName;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerInfoStatus other = (CustomerInfoStatus) obj;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (statusName != other.statusName)
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((statusName == null) ? 0 : statusName.hashCode());
		result = prime * result + version;
		return result;
	}

}
