package com.study.algafood.infrastructure.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.study.algafood.repository.CustomJpaRepository;

public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID>{
	
	private EntityManager manager;
	
	public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager em) {
		super(entityInformation, em);
		// TODO Auto-generated constructor stub
		
		this.manager = em;
	}

	@Override
	public Optional<T> buscarPrimeiro() {
		var jpql = "from "+getDomainClass().getName();
		T entity = manager.createQuery(jpql, getDomainClass())
					       .setMaxResults(1)
					       .getSingleResult();
		return Optional.ofNullable(entity);
	}

	@Override
	public void detach(T entity) {
		manager.detach(entity);
		
	}


}
