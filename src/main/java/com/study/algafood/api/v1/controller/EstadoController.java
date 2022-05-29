package com.study.algafood.api.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.study.algafood.api.v1.converter.EstadoInputDeconvert;
import com.study.algafood.api.v1.converter.EstadoModelConverter;
import com.study.algafood.api.v1.model.EstadoModel;
import com.study.algafood.api.v1.model.input.EstadoInput;
import com.study.algafood.api.v1.openapi.controller.EstadoControllerOpenApi;
import com.study.algafood.core.security.CheckSecurity;
import com.study.algafood.domain.model.Estado;
import com.study.algafood.domain.repository.EstadoRepository;
import com.study.algafood.domain.service.CadastroEstadoService;


@RestController //ja contem o @ResponseBody responsavel por adicionar os resultados dos metodos no corpo da requisicao
@RequestMapping("/v1/estados")
public class EstadoController implements EstadoControllerOpenApi{
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	@Autowired
	private EstadoModelConverter estadoModelConverter;

	@Autowired
	private EstadoInputDeconvert estadoInputDeconvert;   
	
	@CheckSecurity.Estados.PodeConsultar
	@GetMapping
	public CollectionModel<EstadoModel> listar(){
		return estadoModelConverter.toCollectionModel(estadoRepository.findAll());
	}
	
	@CheckSecurity.Estados.PodeConsultar
	@GetMapping("/{estadoId}")
	public EstadoModel buscar(@PathVariable Long estadoId) {
		return estadoModelConverter.toModel(cadastroEstado.buscarOuFalhar(estadoId));
	}
	
	@CheckSecurity.Estados.PodeEditar
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput) {
		
		Estado estado = estadoInputDeconvert.toDomainObject(estadoInput);
		
		return  estadoModelConverter.toModel(cadastroEstado.salvar(estado));
	}
	
	@CheckSecurity.Estados.PodeEditar
	@PutMapping("/{estadoId}")
	public EstadoModel atualizar(@PathVariable Long estadoId,
			@RequestBody @Valid EstadoInput estadoInput) {
		
		Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);
	    
	    //BeanUtils.copyProperties(estado, estadoAtual, "id");
	    
		estadoInputDeconvert.copyToDomainObject(estadoInput, estadoAtual);
		
	    return estadoModelConverter.toModel(cadastroEstado.salvar(estadoAtual));
	}
	
	@CheckSecurity.Estados.PodeEditar
	@DeleteMapping("/{estadoId}")
	public void remover(@PathVariable Long estadoId) {
		cadastroEstado.excluir(estadoId);	
	}

}
