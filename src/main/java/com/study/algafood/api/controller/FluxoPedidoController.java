package com.study.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.study.algafood.api.openapi.controller.FluxoPedidoControllerOpenApi;
import com.study.algafood.domain.service.FluxoPedidoService;


@RestController
@RequestMapping(value = "/pedidos/{pedidoCodigo}")
public class FluxoPedidoController implements FluxoPedidoControllerOpenApi{
	
	@Autowired
	private FluxoPedidoService fluxoPedidoService;
	
	@PutMapping("/confirmacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> confirmar(@PathVariable String pedidoCodigo) {
		fluxoPedidoService.confirmar(pedidoCodigo);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/cancelamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> cancelar(@PathVariable String pedidoCodigo) {
		fluxoPedidoService.cancelar(pedidoCodigo);
		
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/entrega")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> entregar(@PathVariable String pedidoCodigo) {
		fluxoPedidoService.entregar(pedidoCodigo);
		
		return ResponseEntity.noContent().build();
	}
	
}
