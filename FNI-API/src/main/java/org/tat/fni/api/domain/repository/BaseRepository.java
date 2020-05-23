package org.tat.fni.api.domain.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {

	List<T> findByQuery(String query, Map<String, Object> params);
	List<T> findByNamedQuery(String query, Map<String, Object> params);
	Long findCountByQuery(String query, Map<String, Object> params);
	Long findCountByNamedQuery(String query, Map<String, Object> params);
	
	<DTO>List<DTO> findByQuery(String query, Map<String, Object> params, Class<DTO> type);
	<DTO>List<DTO> findByNamedQuery(String query, Map<String, Object> params, Class<DTO> type);
}
