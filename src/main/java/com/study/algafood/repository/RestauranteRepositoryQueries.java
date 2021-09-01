package com.study.algafood.repository;

import java.math.BigDecimal;
import java.util.List;

import com.study.algafood.model.Restaurante;

public interface RestauranteRepositoryQueries {

	List<Restaurante> find(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);
	
	List<Restaurante> findComFreteGratis(String nome);

}