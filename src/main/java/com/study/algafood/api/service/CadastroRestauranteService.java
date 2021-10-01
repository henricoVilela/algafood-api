package com.study.algafood.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.algafood.api.exception.RestauranteNaoEncontradaException;
import com.study.algafood.model.Cidade;
import com.study.algafood.model.Cozinha;
import com.study.algafood.model.FormaPagamento;
import com.study.algafood.model.Restaurante;
import com.study.algafood.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;
	
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
	    Long cozinhaId  = restaurante.getCozinha().getId();
	    Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
	    Cidade cidade   = cadastroCidadeService.buscarOuFalhar(restaurante.getEndereco().getCidade().getId());
	    
	    restaurante.setCozinha(cozinha);
	    restaurante.getEndereco().setCidade(cidade);
	    
	    return restauranteRepository.save(restaurante);
 	}
	
	@Transactional
	public Restaurante buscarOuFalhar(Long restauranteId) {
		
		//Se não tiver ninguem no Optionallança a exception passada
	    return restauranteRepository.findById(restauranteId)
	        .orElseThrow(() -> new RestauranteNaoEncontradaException(restauranteId));
	}
	
	@Transactional
	public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
		
		restaurante.removerFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
		
		restaurante.adicionarFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void ativar(Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		restauranteAtual.ativar();
	}
	
	@Transactional
	public void inativar(Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		restauranteAtual.inativar();
	}
}
