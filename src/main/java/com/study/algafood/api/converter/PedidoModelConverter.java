package com.study.algafood.api.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.study.algafood.api.CreatorLinks;
import com.study.algafood.api.controller.PedidoController;
import com.study.algafood.api.model.PedidoModel;
import com.study.algafood.domain.model.Pedido;

@Component
public class PedidoModelConverter extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private CreatorLinks linkService;

    public PedidoModelConverter() {
        super(PedidoController.class, PedidoModel.class);
    }
    
    @Override
    public PedidoModel toModel(Pedido pedido) {
		PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoModel);

		pedidoModel.add(linkService.linkToPedidos());
		
		if (pedido.podeSerConfirmado()) {
			pedidoModel.add(linkService.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
		}
		
		if (pedido.podeSerCancelado()) {
			pedidoModel.add(linkService.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
		}
		
		if (pedido.podeSerEntregue()) {
			pedidoModel.add(linkService.linkToEntregaPedido(pedido.getCodigo(), "entregar"));
		}
		
		 
		pedidoModel.add(linkService.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
		
		pedidoModel.add(linkService.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
		
		pedidoModel.add(linkService.linkToEntregaPedido(pedido.getCodigo(), "entregar"));
		    
	    pedidoModel.getRestaurante().add(
	    		linkService.linkToRestaurante(pedido.getRestaurante().getId()));
	    
	    pedidoModel.getCliente().add(
	    		linkService.linkToUsuario(pedido.getCliente().getId()));
	    
	    pedidoModel.getFormaPagamento().add(
	    		linkService.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
	    
	    pedidoModel.getEnderecoEntrega().getCidade().add(
	    		linkService.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));
	    
	    pedidoModel.getItens().forEach(item -> {
	        item.add(linkService.linkToProduto(
	                pedidoModel.getRestaurante().getId(), item.getProdutoId(), "produto"));
	    });
		
		return pedidoModel;
	}
    
}