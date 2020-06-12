package org.tat.fni.api.domain.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tat.fni.api.domain.MedicalProposal;

public interface MedicalProposalRepository extends JpaRepository<MedicalProposal, String> {

	@Transactional
	@Query(nativeQuery = true, value = "SELECT ID FROM MEDICALPROPOSAL WHERE PROPOSALNO=?1")
	public String getProposalId(String proposalNo);

	@Transactional
	@Query(nativeQuery = true, value = "SELECT ID FROM MEDICALPOLICY WHERE PROPOSALID=?1")
	public String getPolicylId(String proposalId);

}
