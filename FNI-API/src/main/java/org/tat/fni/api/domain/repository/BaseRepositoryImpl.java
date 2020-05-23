package org.tat.fni.api.domain.repository;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class BaseRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

	private JpaEntityInformation<T, ID> info;
	private EntityManager em;
	
	public BaseRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		
		this.em = entityManager;
		this.info = entityInformation;
	}

	@Override
	public List<T> findByQuery(String query, Map<String, Object> params) {
		return setParams(em.createQuery(query, info.getJavaType()), params).getResultList();
	}

	@Override
	public List<T> findByNamedQuery(String query, Map<String, Object> params) {
		return setParams(em.createNamedQuery(query, info.getJavaType()), params).getResultList();
	}

	@Override
	public Long findCountByQuery(String query, Map<String, Object> params) {
		return setParams(em.createQuery(query, Long.class), params).getSingleResult();
	}

	@Override
	public Long findCountByNamedQuery(String query, Map<String, Object> params) {
		return setParams(em.createNamedQuery(query, Long.class), params).getSingleResult();
	}

	@Override
	public <DTO> List<DTO> findByQuery(String query, Map<String, Object> params, Class<DTO> type) {
		return setParams(em.createQuery(query, type), params).getResultList();
	}

	@Override
	public <DTO> List<DTO> findByNamedQuery(String query, Map<String, Object> params, Class<DTO> type) {
		return setParams(em.createNamedQuery(query, type), params).getResultList();
	}
	
	private <E> TypedQuery<E> setParams(TypedQuery<E> q, Map<String, Object> params) {
		
		if(null != params) {
			for(String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}

		return q;
	}



}
