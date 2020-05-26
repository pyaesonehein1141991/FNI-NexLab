package org.tat.fni.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tat.fni.api.domain.MedicalProposal;

public interface MedicalProposalRepository extends JpaRepository<MedicalProposal, String> {

}
