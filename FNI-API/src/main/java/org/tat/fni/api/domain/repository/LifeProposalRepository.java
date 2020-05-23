package org.tat.fni.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tat.fni.api.domain.lifeproposal.LifeProposal;


public interface LifeProposalRepository extends JpaRepository<LifeProposal, String> {


}
