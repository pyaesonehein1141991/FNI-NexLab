package org.tat.fni.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.tat.fni.api.common.SecurityUser;


public interface UserRepository extends JpaRepository<SecurityUser, Integer>{
	
	 boolean existsByUsername(String username);

	  SecurityUser findByUsername(String username);

	  @Transactional
	  void deleteByUsername(String username);

}
