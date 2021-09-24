package com.study.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.study.algafood.api.converter.EstadoInputDeconvert;
import com.study.algafood.api.converter.EstadoModelConverter;
import com.study.algafood.api.model.EstadoModel;
import com.study.algafood.api.model.input.EstadoInput;
import com.study.algafood.api.service.CadastroEstadoService;
import com.study.algafood.model.Estado;
import com.study.algafood.repository.EstadoRepository;


@RestController //ja contem o @ResponseBody responsavel por adicionar os resultados dos metodos no corpo da requisicao
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	@Autowired
	private EstadoModelConverter estadoModelConverter;

	@Autowired
	private EstadoInputDeconvert estadoInputDeconvert;   
	
	@GetMapping
	public List<EstadoModel> listar(){
		return estadoModelConverter.toCollectionModel(estadoRepository.findAll());
	}
	
	@GetMapping("/{estadoId}")
	public EstadoModel buscar(@PathVariable Long estadoId) {
		return estadoModelConverter.toModel(cadastroEstado.buscarOuFalhar(estadoId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput) {
		
		Estado estado = estadoInputDeconvert.toDomainObject(estadoInput);
		
		return  estadoModelConverter.toModel(cadastroEstado.salvar(estado));
	}
	
	@PutMapping("/{estadoId}")
	public EstadoModel atualizar(@PathVariable Long estadoId,
			@RequestBody @Valid EstadoInput estadoInput) {
		
		Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);
	    
	    //BeanUtils.copyProperties(estado, estadoAtual, "id");
	    
		estadoInputDeconvert.copyToDomainObject(estadoInput, estadoAtual);
		
	    return estadoModelConverter.toModel(cadastroEstado.salvar(estadoAtual));
	}
	
	@DeleteMapping("/{estadoId}")
	public void remover(@PathVariable Long estadoId) {
		cadastroEstado.excluir(estadoId);	
	}

}
