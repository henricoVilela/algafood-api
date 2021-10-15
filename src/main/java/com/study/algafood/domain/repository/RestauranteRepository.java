package com.study.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.study.algafood.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante>{
	
	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	
}

/*
public interface RestauranteRepository {
	
	public List<Restaurante> listar();
	public Restaurante buscar(Long id);
	public Restaurante adicionar(Restaurante restaurante);
	public void remover(Restaurante restaurante);
}*/
