package org.tat.fni.api.domain.repository;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.tat.fni.api.domain.lifepolicy.LifePolicy;
import org.tat.fni.api.domain.lifeproposal.LifeProposal;


public interface LifeProposalRepository extends JpaRepository<LifeProposal, String> {

  @Transactional
  @Modifying
  @Query(nativeQuery = true,
      value = "INSERT INTO WORKFLOW VALUES (?1,?2,?3,NULL,'UNDERWRITING',1,'SURVEY',?4,'INUSR001001000000000107062019',NULL,NULL,'INUSR001001000000000107062019','INUSR001001000000000107062019','BANCH00000000000000129032013')")
  public void saveToWorkflow(String id, String referenceNo, String referenceType,
      String createdDate);


  @Transactional
  @Modifying
  @Query(nativeQuery = true,
      value = "INSERT INTO WORKFLOW_HIST VALUES (?1,?2,?3,NULL,1,?5,'SURVEY',?4,'INUSR001001000000000107062019',NULL,NULL,'INUSR001001000000000107062019','INUSR001001000000000107062019','UNDERWRITING','BANCH00000000000000129032013')")
  public void saveToWorkflowHistory(String id, String referenceNo, String referenceType,
      String createdDate, String workflowDate);
  
  @Transactional
  @Query(nativeQuery = true,
  	value = "SELECT ID FROM LIFEPROPOSAL WHERE PROPOSALNO=?1")
  public String getProposalId(String proposalNo);
  
  @Transactional
  @Query(nativeQuery = true,
  	value = "SELECT ID FROM LIFEPOLICY WHERE PROPOSALID=?1")
  public String getPolicylId(String proposalId);
  
  @Transactional
  @Query(nativeQuery = true,
  	value = "SELECT APPROVED FROM LIFEPROPOSAL_INSUREDPERSON_LINK WHERE LIFEPROPOSALID=?1;")
  public List<Boolean> getApprovalStatus(String proposalId);
  
}
