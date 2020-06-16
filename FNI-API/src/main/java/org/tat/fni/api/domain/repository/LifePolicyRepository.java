package org.tat.fni.api.domain.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tat.fni.api.domain.lifepolicy.LifePolicy;

public interface LifePolicyRepository extends JpaRepository<LifePolicy, String> {

	public Optional<LifePolicy> findByPolicyNo(String policyNo);

	@Transactional
	@Query(nativeQuery = true, value = "SELECT * FROM LIFEPOLICY WHERE PROPOSALID=?1;")
	public List<LifePolicy> getPolicyList(String proposalId);

}
