package org.tat.fni.api.domain;

import org.tat.fni.api.common.emumdata.ReferenceType;
import org.tat.fni.api.common.emumdata.TransactionType;
import org.tat.fni.api.common.emumdata.WorkflowTask;

public class WorkFlowDTO {
  private String referenceNo;
  private String branchId;
  private String remark;
  private WorkflowTask workflowTask;
  private ReferenceType referenceType;
  private User createdUser;
  private User responsiblePerson;
  private TransactionType transactionType;

  public WorkFlowDTO(String referenceNo, String branchId, String remark, WorkflowTask workflowTask,
      ReferenceType referenceType, TransactionType transactionType, User createdUser,
      User responsiblePerson) {
    this.branchId = branchId;
    this.referenceNo = referenceNo;
    this.remark = remark;
    this.workflowTask = workflowTask;
    this.transactionType = transactionType;
    this.referenceType = referenceType;
    this.createdUser = createdUser;
    this.responsiblePerson = responsiblePerson;
  }

  public WorkFlowDTO(String referenceNo, String remark, WorkflowTask workflowTask,
      ReferenceType referenceType, User createdUser, User responsiblePerson) {
    this.referenceNo = referenceNo;
    this.remark = remark;
    this.workflowTask = workflowTask;
    this.referenceType = referenceType;
    this.createdUser = createdUser;
    this.responsiblePerson = responsiblePerson;
  }

  public String getReferenceNo() {
    return referenceNo;
  }

  public void setReferenceNo(String referenceNo) {
    this.referenceNo = referenceNo;
  }

  public String getBranchId() {
    return branchId;
  }

  public void setBranchId(String branchId) {
    this.branchId = branchId;
  }

  public String getRemark() {
    return remark;
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

  public ReferenceType getReferenceType() {
    return referenceType;
  }

  public void setReferenceType(ReferenceType referenceType) {
    this.referenceType = referenceType;
  }

  public User getCreatedUser() {
    return createdUser;
  }

  public void setCreatedUser(User createdUser) {
    this.createdUser = createdUser;
  }

  public User getResponsiblePerson() {
    return responsiblePerson;
  }

  public void setResponsiblePerson(User responsiblePerson) {
    this.responsiblePerson = responsiblePerson;
  }

  public TransactionType getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(TransactionType transactionType) {
    this.transactionType = transactionType;
  }

}
