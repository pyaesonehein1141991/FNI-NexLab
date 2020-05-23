package org.tat.fni.api.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tat.fni.api.domain.Agent;

public interface AgentRepository extends JpaRepository<Agent, String> {

  @Query(value = "SELECT * FROM AGENT", nativeQuery = true)
  List<Object[]> findAllNativeObject();

  @Query(value = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'AGENT'",
      nativeQuery = true)
  List<Object> findAllColumnName();

}
