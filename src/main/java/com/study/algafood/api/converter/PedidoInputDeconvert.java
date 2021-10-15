package com.study.algafood.api.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.study.algafood.api.model.input.PedidoInput;
import com.study.algafood.domain.model.Pedido;

@Component
public class PedidoInputDeconvert {

    @Autowired
    private ModelMapper modelMapper;
    
    public Pedido toDomainObject(PedidoInput pedidoInput) {
        return modelMapper.map(pedidoInput, Pedido.class);
    }
    
    public void copyToDomainObject(PedidoInput pedidoInput, Pedido pedido) {
        modelMapper.map(pedidoInput, pedido);
    }   
}
