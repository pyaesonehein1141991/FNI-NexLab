package org.tat.fni.api.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tat.fni.api.domain.School;

public interface SchoolRepository extends JpaRepository<School, String> {

  @Query(value = "SELECT * FROM  SaleMan", nativeQuery = true)
  List<Object[]> findAllNativeObject();

  @Query(value = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'SaleMan'",
      nativeQuery = true)
  List<Object> findAllColumnName();

}
