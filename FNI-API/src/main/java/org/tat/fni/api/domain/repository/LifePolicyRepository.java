package org.tat.fni.api.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tat.fni.api.domain.lifepolicy.LifePolicy;

public interface LifePolicyRepository extends JpaRepository<LifePolicy, String>{
	
	public Optional<LifePolicy> findByPolicyNo(String policyNo);
	
	
  

}
