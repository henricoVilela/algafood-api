package com.study.algafood.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.algafood.domain.model.Pedido;
import com.study.algafood.domain.repository.PedidoRepository;

@Service
public class FluxoPedidoService {
	
	@Autowired
	private EmissaoPedidoService emissaoPedido;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Transactional
	public void confirmar(String pedidoCodigo) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoCodigo);
	
		pedido.confirmar();
		pedidoRepository.save(pedido);//save para poder funcionar a emissao de evento
		
	}
	
	@Transactional
	public void cancelar(String pedidoCodigo) {
	    Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoCodigo);
	    
	    pedido.cancelar();
	    pedidoRepository.save(pedido);
	}
	
	@Transactional
	public void entregar(String pedidoCodigo) {
	    Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoCodigo);
	    
	    pedido.entregar();
	    pedido.setDataEntrega(OffsetDateTime.now());
	}

}
