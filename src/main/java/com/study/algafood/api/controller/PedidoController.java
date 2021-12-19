package com.study.algafood.api.controller;

import java.util.Map;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.study.algafood.api.model.PedidoModel;
import com.study.algafood.api.model.PedidoResumoModel;
import com.study.algafood.api.model.input.PedidoInput;
import com.study.algafood.api.openapi.controller.PedidoControllerOpenApi;
import com.study.algafood.core.data.PageWrapper;
import com.study.algafood.core.data.PageableTranslator;
import com.study.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.study.algafood.domain.exception.NegocioException;
import com.study.algafood.domain.filter.PedidoFilter;
import com.study.algafood.domain.model.Pedido;
import com.study.algafood.domain.model.Usuario;
import com.study.algafood.domain.repository.PedidoRepository;
import com.study.algafood.domain.service.EmissaoPedidoService;
import com.study.algafood.infrastructure.repository.spec.PedidoSpecs;

@RestController
@RequestMapping(path = "/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi{

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
    
    @Autowired
    private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;
    
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
    
	@Override
	@GetMapping
	public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro, 
			@PageableDefault(size = 10) Pageable pageable) {
		Pageable pageableTraduzido = traduzirPageable(pageable);
		
		Page<Pedido> pedidosPage = pedidoRepository.findAll(
				PedidoSpecs.usandoFiltro(filtro), pageableTraduzido);
		
		pedidosPage = new PageWrapper<>(pedidosPage, pageable);
		
		return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoModelConverter);
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
    
    private Pageable traduzirPageable(Pageable apiPageable) {
    	var mapeamento = Map.of(
				"codigo", "codigo",
				"subtotal", "subtotal",
				"taxaFrete", "taxaFrete",
				"valorTotal", "valorTotal",
				"dataCriacao", "dataCriacao",
				"restaurante.nome", "restaurante.nome",
				"restaurante.id", "restaurante.id",
				"cliente.id", "cliente.id",
				"nomeCliente", "cliente.nome"
			);
		
		return PageableTranslator.translate(apiPageable, mapeamento);
	}
}   