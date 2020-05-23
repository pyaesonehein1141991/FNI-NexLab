package org.tat.fni.api.domain.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.tat.fni.api.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {

  // List<Customer> findByRecorderCreatedDateBetweenOrRecorderUpdatedDateBetween(Date
  // createdDate,Date updatedDate,Date createdDate1,Date updatedDate1);

  @Query(
      value = "SELECT * FROM CUSTOMER WHERE CREATEDDATE BETWEEN ?1 AND ?2 OR UPDATEDDATE BETWEEN ?3 AND ?4",
      nativeQuery = true)
  List<Object[]> findAllNativeObject(@Param("date1") Date createdDate,
      @Param("date2") Date updatedDate, @Param("date3") Date createddate1,
      @Param("date4") Date updatedDate1);

  @Query(value = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'AGENT'",
      nativeQuery = true)
  List<Object> findAllColumnName();


}
