package org.tat.fni.api.domain;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.eclipse.persistence.annotations.CascadeOnDelete;
import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;
import org.tat.fni.api.common.emumdata.TransactionType;
import org.tat.fni.api.common.emumdata.WorkFlowType;
import org.tat.fni.api.common.emumdata.WorkflowTask;


@Entity
@Table(name = TableName.USER_AUTHORITY)
@TableGenerator(name = "AUTHORITY_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "AUTHORITY_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class Authority implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AUTHORITY_GEN")
	private String id;

	@Column(name = "INSURANCETYPE")
	@Enumerated(EnumType.STRING)
	private WorkFlowType workFlowType;

	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;

	@ElementCollection(targetClass = WorkflowTask.class)
	@Enumerated(EnumType.STRING)
	@CascadeOnDelete
	@Column(name = "PERMISSION")
	@CollectionTable(name = "USER_PERMISSION", joinColumns = @JoinColumn(name = "AUTHORITYID", referencedColumnName = "ID"))
	private List<WorkflowTask> permissionList;

	@Embedded
	private UserRecorder recorder;

	@Version
	private int version;

	public Authority() {
	}

	public Authority(WorkFlowType workFlowType, TransactionType transactionType, List<WorkflowTask> permissionList) {
		this.workFlowType = workFlowType;
		this.transactionType = transactionType;
		this.permissionList = permissionList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public WorkFlowType getWorkFlowType() {
		return workFlowType;
	}

	public void setWorkFlowType(WorkFlowType workFlowType) {
		this.workFlowType = workFlowType;
	}

	public List<WorkflowTask> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<WorkflowTask> permissionList) {
		this.permissionList = permissionList;
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

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + version;
		result = prime * result + ((workFlowType == null) ? 0 : workFlowType.hashCode());
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
		Authority other = (Authority) obj;
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
		if (version != other.version)
			return false;
		if (workFlowType != other.workFlowType)
			return false;
		return true;
	}

}
