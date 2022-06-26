package com.study.algafood.infrastructure.repository;

import static com.study.algafood.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static com.study.algafood.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.study.algafood.domain.model.Restaurante;
import com.study.algafood.domain.repository.RestauranteRepository;
import com.study.algafood.domain.repository.RestauranteRepositoryQueries;

/*
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.study.algafood.model.Restaurante;
import com.study.algafood.repository.RestauranteRepository;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepository{
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Restaurante> listar() {
		TypedQuery<Restaurante> query = manager.createQuery("from Restaurante", Restaurante.class);
		
		return query.getResultList();
	}

	@Override
	public Restaurante buscar(Long id) {
		return manager.find(Restaurante.class, id);
	}

	@Override
	@Transactional
	public Restaurante adicionar(Restaurante restaurante) {
		return manager.merge(restaurante);
	}

	@Override
	@Transactional
	public void remover(Restaurante restaurante) {
		restaurante = buscar(restaurante.getId());
		manager.remove(restaurante);
		
	}

}
*/

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired @Lazy
    private RestauranteRepository restauranteRepository;
	
	@Override
	public List<Restaurante> find(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Restaurante> cQuery = builder.createQuery(Restaurante.class);
		
		Root<Restaurante> root = cQuery.from(Restaurante.class);
		
		var predicates = new ArrayList<Predicate>();
		
		if(StringUtils.hasText(nome)){predicates.add(builder.like(root.get("nome"), "%"+nome+"%"));}
		if(taxaInicial != null){      predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaInicial));}
		if(taxaFinal != null){        predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFinal));}
		//Predicate nomePredicate = builder.like(root.get("nome"), "%"+nome+"%");
		//Predicate taxaInicialPredicate = builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaInicial);
		//Predicate taxaFinalPredicate   = builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFinal);
		
		cQuery.where(predicates.toArray(new Predicate[0]));
		
		TypedQuery<Restaurante> query = manager.createQuery(cQuery);
		
					
		return query.getResultList();
	}

	@Override
	public List<Restaurante> findComFreteGratis(String nome) {
		
		return restauranteRepository.findAll(comFreteGratis().and(comNomeSemelhante(nome)));
		
	}
}