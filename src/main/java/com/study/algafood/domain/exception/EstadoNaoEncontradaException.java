package com.study.algafood.domain.exception;


public class EstadoNaoEncontradaException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;
	
	public EstadoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public EstadoNaoEncontradaException(Long id) {
		this(String.format("Não existe um cadastro de estado com código %d", id));
	}
}
