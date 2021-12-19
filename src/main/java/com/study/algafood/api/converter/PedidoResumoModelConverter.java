package com.study.algafood.api.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.study.algafood.api.CreatorLinks;
import com.study.algafood.api.controller.PedidoController;
import com.study.algafood.api.model.PedidoResumoModel;
import com.study.algafood.domain.model.Pedido;

@Component
public class PedidoResumoModelConverter extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
	private CreatorLinks linkService;

    public PedidoResumoModelConverter() {
        super(PedidoController.class, PedidoResumoModel.class);
    }
    
    @Override
    public PedidoResumoModel toModel(Pedido pedido) {
        PedidoResumoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);
        
        pedidoModel.add(linkService.linkToPedidos());
        
        pedidoModel.getRestaurante().add(linkService.linkToRestaurante(pedido.getRestaurante().getId()));

        pedidoModel.getCliente().add(linkService.linkToUsuario(pedido.getCliente().getId()));
        
        return pedidoModel;
    }
    
}
