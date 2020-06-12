package org.tat.fni.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tat.fni.api.domain.MedicalPolicy;

public interface MedicalPolicyRepository extends JpaRepository<MedicalPolicy, String>{

}
