package com.study.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.study.algafood.api.converter.PedidoInputDeconvert;
import com.study.algafood.api.converter.PedidoModelConverter;
import com.study.algafood.api.converter.PedidoResumoModelConverter;
import com.study.algafood.api.exception.EntidadeNaoEncontradaException;
import com.study.algafood.api.exception.NegocioException;
import com.study.algafood.api.model.PedidoModel;
import com.study.algafood.api.model.PedidoResumoModel;
import com.study.algafood.api.model.input.PedidoInput;
import com.study.algafood.api.service.EmissaoPedidoService;
import com.study.algafood.infrastructure.repository.spec.PedidoSpecs;
import com.study.algafood.model.Pedido;
import com.study.algafood.model.Usuario;
import com.study.algafood.repository.PedidoRepository;
import com.study.algafood.repository.filter.PedidoFilter;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private EmissaoPedidoService emissaoPedido;
    
    @Autowired
    private PedidoModelConverter pedidoModelConverter;
    
    @Autowired
    private PedidoResumoModelConverter pedidoResumoModelConverter;
    
    @Autowired
    private PedidoInputDeconvert pedidoInputDeconvert;
    
    /*@GetMapping
    public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
        List<Pedido> todosPedidos = pedidoRepository.findAll();
        List<PedidoResumoModel> pedidosModel = pedidoResumoModelConverter.toCollectionModel(todosPedidos);

        MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel);
        
        //Adicioa filtros à representacao
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
        
        if(StringUtils.isNoneBlank(campos))
        	filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
        
        pedidosWrapper.setFilters(filterProvider);
        
        return pedidosWrapper;
    }*/
    
    @GetMapping
    public Page<PedidoResumoModel> pesquisar(PedidoFilter filtro, @PageableDefault(size = 15) Pageable pageable) {
    	
    	pageable = traduzirPageable(pageable);
    	
        Page<Pedido> pedidosPage = pedidoRepository.findAll(
                PedidoSpecs.usandoFiltro(filtro), pageable);
        
        List<PedidoResumoModel> pedidosResumoModel = pedidoResumoModelConverter
                .toCollectionModel(pedidosPage.getContent());
        
        Page<PedidoResumoModel> pedidosResumoModelPage = new PageImpl<>(
                pedidosResumoModel, pageable, pedidosPage.getTotalElements());
        
        return pedidosResumoModelPage;
    }
    
    @GetMapping("/{pedidoCodigo}")
    public PedidoModel buscar(@PathVariable String pedidoCodigo) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoCodigo);
        
        return pedidoModelConverter.toModel(pedido);
    }            
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = pedidoInputDeconvert.toDomainObject(pedidoInput);

            // TODO pegar usuário autenticado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            novoPedido = emissaoPedido.emitir(novoPedido);

            return pedidoModelConverter.toModel(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }
    
    public Pageable traduzirPageable(Pageable apiPageable) {
    	
    	return null;
    }
}   