package com.study.algafood.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.algafood.domain.model.Pedido;
import com.study.algafood.domain.service.EnvioEmailService.Mensagem;

@Service
public class FluxoPedidoService {
	
	@Autowired
	private EmissaoPedidoService emissaoPedido;
	
	@Autowired
	private EnvioEmailService envioEmail;
	
	@Transactional
	public void confirmar(String pedidoCodigo) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoCodigo);
	
		pedido.confirmar();
		pedido.setDataConfirmacao(OffsetDateTime.now());
		
		var mensagem = Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
				.corpo("pedido-confirmado.html")
				.variavel("pedido", pedido)
				.destinatario(pedido.getCliente().getEmail())
				.build();
		
		envioEmail.enviar(mensagem);
		
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
