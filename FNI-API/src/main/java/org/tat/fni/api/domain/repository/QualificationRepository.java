package org.tat.fni.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tat.fni.api.domain.Qualification;

public interface QualificationRepository extends JpaRepository<Qualification, String>{

}
