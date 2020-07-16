package org.tat.fni.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tat.fni.api.domain.Industry;

public interface IndustryRepository extends JpaRepository<Industry, String> {

}
