package org.tat.fni.api.domain.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tat.fni.api.domain.MedicalPolicy;
import org.tat.fni.api.domain.lifepolicy.LifePolicy;

public interface MedicalPolicyRepository extends JpaRepository<MedicalPolicy, String>{
	
	@Transactional
	@Query(nativeQuery = true, value = "SELECT * FROM MEDICALPOLICY WHERE PROPOSALID=?1;")
	public List<MedicalPolicy> getPolicyList(String proposalId);

}
