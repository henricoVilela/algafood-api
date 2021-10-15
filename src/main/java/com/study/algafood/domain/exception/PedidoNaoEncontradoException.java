package com.study.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public PedidoNaoEncontradoException(Long pedidoId) {
        super(String.format("Não existe um pedido com código %d", pedidoId));
    }   
    
    public PedidoNaoEncontradoException(String pedidoCodigo) {
        super(String.format("Não existe um pedido com código %s", pedidoCodigo));
    } 
}