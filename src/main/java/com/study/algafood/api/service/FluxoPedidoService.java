package com.study.algafood.api.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.algafood.model.Pedido;

@Service
public class FluxoPedidoService {
	
	@Autowired
	private EmissaoPedidoService emissaoPedido;
	
	@Transactional
	public void confirmar(String pedidoCodigo) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoCodigo);
	
		pedido.confirmar();
		pedido.setDataConfirmacao(OffsetDateTime.now());
	}
	
	@Transactional
	public void cancelar(String pedidoCodigo) {
	    Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoCodigo);
	    
	    pedido.cancelar();
	    pedido.setDataCancelamento(OffsetDateTime.now());
	}
	
	@Transactional
	public void entregar(String pedidoCodigo) {
	    Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoCodigo);
	    
	    pedido.entregar();
	    pedido.setDataEntrega(OffsetDateTime.now());
	}

}
