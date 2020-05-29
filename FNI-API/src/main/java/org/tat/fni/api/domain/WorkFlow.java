package org.tat.fni.api.domain;

import java.io.Serializable;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;
import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;
import org.tat.fni.api.common.emumdata.ReferenceType;
import org.tat.fni.api.common.emumdata.TransactionType;
import org.tat.fni.api.common.emumdata.WorkflowTask;



@Entity
@Table(name = TableName.WORKFLOW)
@TableGenerator(name = "WORKFLOW_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME",
    valueColumnName = "GEN_VAL", pkColumnValue = "WORKFLOW_GEN", allocationSize = 10)
@NamedQueries(value = {@NamedQuery(name = "WorkFlow.findAll", query = "SELECT a FROM WorkFlow a "),
    @NamedQuery(name = "WorkFlow.findByReferenceNo",
        query = "SELECT a FROM WorkFlow a WHERE a.referenceNo = :referenceNo"),
    @NamedQuery(name = "WorkFlow.findByUser",
        query = "SELECT a FROM WorkFlow a WHERE a.responsiblePerson.usercode = :usercode"),
    @NamedQuery(name = "WorkFlow.findByRefType",
        query = "SELECT a FROM WorkFlow a WHERE a.referenceType = :referenceType"),
    @NamedQuery(name = "WorkFlow.findById", query = "SELECT a FROM WorkFlow a WHERE a.id = :id")})
@EntityListeners(IDInterceptor.class)
public class WorkFlow implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "WORKFLOW_GEN")
  private String id;
  private String branchId;
  private String referenceNo;
  private String remark;

  @Enumerated(value = EnumType.STRING)
  private WorkflowTask workflowTask;

  @Enumerated(EnumType.STRING)
  private TransactionType transactionType;

  @Enumerated(value = EnumType.STRING)
  private ReferenceType referenceType;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "REQUESTORID", referencedColumnName = "ID")
  private User createdUser;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "RESPONSIBLEUSERID", referencedColumnName = "ID")
  private User responsiblePerson;

  @Embedded
  private UserRecorder recorder;

  @Version
  private int version;

  public WorkFlow() {}

  public WorkFlow(WorkFlowDTO workFlowDTO) {
    this.referenceNo = workFlowDTO.getReferenceNo();
    this.branchId = workFlowDTO.getBranchId();
    this.referenceType = workFlowDTO.getReferenceType();
    this.createdUser = workFlowDTO.getCreatedUser();
    this.transactionType = workFlowDTO.getTransactionType();
    this.responsiblePerson = workFlowDTO.getResponsiblePerson();
    this.remark = workFlowDTO.getRemark();
    this.workflowTask = workFlowDTO.getWorkflowTask();
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

  public String getRemark() {
    return this.remark;
  }

  public String getReferenceNo() {
    return referenceNo;
  }

  public String getBranchId() {
    return branchId;
  }

  public void setBranchId(String branchId) {
    this.branchId = branchId;
  }

  public void setReferenceNo(String referenceNo) {
    this.referenceNo = referenceNo;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public WorkflowTask getWorkflowTask() {
    return workflowTask;
  }

  public void setWorkflowTask(WorkflowTask workflowTask) {
    this.workflowTask = workflowTask;
  }

  public User getCreatedUser() {
    return createdUser;
  }

  public void setCreatedUser(User createdUser) {
    this.createdUser = createdUser;
  }

  public User getResponsiblePerson() {
    return this.responsiblePerson;
  }

  public void setResponsiblePerson(User responsiblePerson) {
    this.responsiblePerson = responsiblePerson;
  }

  public ReferenceType getReferenceType() {
    return referenceType;
  }

  public void setReferenceType(ReferenceType referenceType) {
    this.referenceType = referenceType;
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
    result = prime * result + ((branchId == null) ? 0 : branchId.hashCode());
    result = prime * result + ((createdUser == null) ? 0 : createdUser.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
    result = prime * result + ((referenceNo == null) ? 0 : referenceNo.hashCode());
    result = prime * result + ((referenceType == null) ? 0 : referenceType.hashCode());
    result = prime * result + ((remark == null) ? 0 : remark.hashCode());
    result = prime * result + ((responsiblePerson == null) ? 0 : responsiblePerson.hashCode());
    result = prime * result + ((transactionType == null) ? 0 : transactionType.hashCode());
    result = prime * result + version;
    result = prime * result + ((workflowTask == null) ? 0 : workflowTask.hashCode());
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
    WorkFlow other = (WorkFlow) obj;
    if (branchId == null) {
      if (other.branchId != null)
        return false;
    } else if (!branchId.equals(other.branchId))
      return false;
    if (createdUser == null) {
      if (other.createdUser != null)
        return false;
    } else if (!createdUser.equals(other.createdUser))
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
    if (referenceNo == null) {
      if (other.referenceNo != null)
        return false;
    } else if (!referenceNo.equals(other.referenceNo))
      return false;
    if (referenceType != other.referenceType)
      return false;
    if (remark == null) {
      if (other.remark != null)
        return false;
    } else if (!remark.equals(other.remark))
      return false;
    if (responsiblePerson == null) {
      if (other.responsiblePerson != null)
        return false;
    } else if (!responsiblePerson.equals(other.responsiblePerson))
      return false;
    if (transactionType != other.transactionType)
      return false;
    if (version != other.version)
      return false;
    if (workflowTask != other.workflowTask)
      return false;
    return true;
  }
}
