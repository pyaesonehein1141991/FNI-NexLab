package org.tat.fni.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.tat.fni.api.domain.lifeproposal.LifeProposal;


public interface LifeProposalRepository extends JpaRepository<LifeProposal, String> {

	@Modifying
	@Query(nativeQuery = true, value = "INSERT INTO WORKFLOW VALUES (?1,?2,?3,NULL,'UNDERWRITING',1,'SURVEY',?4,'INUSR001001000000000107062019',NULL,NULL,'INUSR001001000000000107062019','INUSR001001000000000107062019','BANCH00000000000000129032013')")
	public void saveToWorkflow(String id, String referenceNo, String referenceType,String createdDate); 

}
