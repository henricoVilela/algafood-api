package com.study.algafood.api.openapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.study.algafood.api.exceptionhandler.Problem;
import com.study.algafood.api.model.PedidoModel;
import com.study.algafood.api.model.PedidoResumoModel;
import com.study.algafood.api.model.input.PedidoInput;
import com.study.algafood.domain.filter.PedidoFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {

    @ApiOperation("Pesquisa os pedidos")
    public Page<PedidoResumoModel> pesquisar(PedidoFilter filtro, Pageable pageable);
    
    @ApiOperation("Registra um pedido")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Pedido registrado"),
    })
    public PedidoModel adicionar(
            @ApiParam(name = "corpo", value = "Representação de um novo pedido")
            PedidoInput pedidoInput);
    
    @ApiOperation("Busca um pedido por código")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    public PedidoModel buscar(
            @ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
            String codigoPedido);   
}