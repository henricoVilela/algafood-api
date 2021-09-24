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

import com.study.algafood.api.converter.CidadeInputDeconvert;
import com.study.algafood.api.converter.CidadeModelConverter;
import com.study.algafood.api.exception.EstadoNaoEncontradaException;
import com.study.algafood.api.exception.NegocioException;
import com.study.algafood.api.model.CidadeModel;
import com.study.algafood.api.model.input.CidadeInput;
import com.study.algafood.api.service.CadastroCidadeService;
import com.study.algafood.model.Cidade;
import com.study.algafood.repository.CidadeRepository;

@RestController //ja contem o @ResponseBody responsavel por adicionar os resultados dos metodos no corpo da requisicao
@RequestMapping("/cidades")
public class CidadeController {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private CidadeModelConverter cidadeModelConverter;

	@Autowired
	private CidadeInputDeconvert cidadeInputDeconvert;       
	
	@GetMapping
	public List<CidadeModel> listar(){
		return cidadeModelConverter.toCollectionModel(cidadeRepository.findAll());
	}
	
	@GetMapping("/{cidadeId}")
	public CidadeModel buscar(@PathVariable Long cidadeId){
		return cidadeModelConverter.toModel(cadastroCidade.buscarOuFalhar(cidadeId));
	}
	
	@PostMapping
	public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput){		
		try {
			Cidade cidade = cidadeInputDeconvert.toDomainObject(cidadeInput);
			
			return cidadeModelConverter.toModel(cadastroCidade.salvar(cidade));
	    } catch (EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@PutMapping("/{cidadeId}")
	public CidadeModel atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput){
		Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);
	    
	    //BeanUtils.copyProperties(cidade, cidadeAtual, "id");
		
		cidadeInputDeconvert.copyToDomainObject(cidadeInput, cidadeAtual);
		
	    try {
	    	return cidadeModelConverter.toModel(cadastroCidade.salvar(cidadeAtual));
	    } catch (EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	    
	}
	
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {
	    cadastroCidade.excluir(cidadeId);	
	}
	
	
}
