package com.study.algafood.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.study.algafood.domain.event.PedidoConfirmadoEvent;
import com.study.algafood.domain.model.Pedido;
import com.study.algafood.domain.service.EnvioEmailService;
import com.study.algafood.domain.service.EnvioEmailService.Mensagem;

@Component
public class NotificacaoClientePedidoConfirmado {
	
	@Autowired
	private EnvioEmailService envioEmail;
	
	@TransactionalEventListener//Anotacao para ouvir o evento apos o flush no banco
	public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
		
		Pedido pedido = event.getPedido();
		
		var mensagem = Mensagem.builder()
		.assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
		.corpo("pedido-confirmado.html")
		.variavel("pedido", pedido)
		.destinatario(pedido.getCliente().getEmail())
		.build();

		envioEmail.enviar(mensagem);
	}
}
