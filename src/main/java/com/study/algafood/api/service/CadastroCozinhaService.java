package com.study.algafood.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.algafood.api.exception.CozinhaNaoEncontradaException;
import com.study.algafood.api.exception.EntidadeEmUsoException;
import com.study.algafood.model.Cozinha;
import com.study.algafood.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {
	
	private static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso.";
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Transactional
 	public Cozinha salvar(Cozinha cozinha) {
 		return cozinhaRepository.save(cozinha);
 	}
 	
	@Transactional
 	public void excluir(Long CozinhaId) {
 		try {
			cozinhaRepository.deleteById(CozinhaId);
			cozinhaRepository.flush();
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, CozinhaId));
		}catch (EmptyResultDataAccessException e) {
			throw new CozinhaNaoEncontradaException(CozinhaId);
		}
 	}
 	
 	public Cozinha buscarOuFalhar(Long id) {
 		return cozinhaRepository.findById(id)
				.orElseThrow(() -> new CozinhaNaoEncontradaException(id));
 	}
}
