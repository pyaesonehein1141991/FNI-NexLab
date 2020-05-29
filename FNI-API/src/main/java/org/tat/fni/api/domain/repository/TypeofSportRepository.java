package org.tat.fni.api.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tat.fni.api.domain.TypesOfSport;

public interface TypeofSportRepository extends JpaRepository<TypesOfSport, String> {


  @Query(value = "SELECT * FROM TYPESOFSPORT", nativeQuery = true)
  List<Object[]> findAllNativeObject();

  @Query(
      value = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'TYPESOFSPORT'",
      nativeQuery = true)
  List<Object> findAllColumnName();

}
