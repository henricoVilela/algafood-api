package com.study.algafood.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.algafood.api.exception.RestauranteNaoEncontradaException;
import com.study.algafood.model.Cozinha;
import com.study.algafood.model.Restaurante;
import com.study.algafood.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
	    Long cozinhaId = restaurante.getCozinha().getId();
	    Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
	    restaurante.setCozinha(cozinha);
	    
	    return restauranteRepository.save(restaurante);
 	}
	
	@Transactional
	public Restaurante buscarOuFalhar(Long restauranteId) {
		//Se não tiver ninguem no Optionallança a exception passada
	    return restauranteRepository.findById(restauranteId)
	        .orElseThrow(() -> new RestauranteNaoEncontradaException(restauranteId));
	}
}
