package com.study.algafood.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.study.algafood.api.v1.ResourceUriHelper;
import com.study.algafood.api.v1.converter.CidadeInputDeconvert;
import com.study.algafood.api.v1.converter.CidadeModelConverter;
import com.study.algafood.api.v1.model.CidadeModel;
import com.study.algafood.api.v1.model.input.CidadeInput;
import com.study.algafood.api.v1.openapi.controller.CidadeControllerOpenApi;
import com.study.algafood.core.security.CheckSecurity;
import com.study.algafood.domain.exception.EstadoNaoEncontradaException;
import com.study.algafood.domain.exception.NegocioException;
import com.study.algafood.domain.model.Cidade;
import com.study.algafood.domain.repository.CidadeRepository;
import com.study.algafood.domain.service.CadastroCidadeService;

import io.swagger.annotations.ApiParam;


@RestController //ja contem o @ResponseBody responsavel por adicionar os resultados dos metodos no corpo da requisicao
//@RequestMapping(path="/cidades", produces = AlgaMediaTypes.V1_APPLICATION_JSON_VALUE)
@RequestMapping(path="/v1/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private CidadeModelConverter cidadeModelConverter;

	@Autowired
	private CidadeInputDeconvert cidadeInputDeconvert;       
	
	
	@Override
	@CheckSecurity.Cidades.PodeConsultar
	@GetMapping
	public CollectionModel<CidadeModel> listar() {
		List<Cidade> todasCidades = cidadeRepository.findAll();
		
		return cidadeModelConverter.toCollectionModel(todasCidades);
	}
	
	@CheckSecurity.Cidades.PodeConsultar
	@GetMapping("/{cidadeId}")
	public CidadeModel buscar(@ApiParam(value = "ID de uma cidade", example = "1", required = true) @PathVariable Long cidadeId){
		return cidadeModelConverter.toModel(cadastroCidade.buscarOuFalhar(cidadeId));
	}
	
	@CheckSecurity.Cidades.PodeEditar
	@PostMapping
	public CidadeModel adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true) @RequestBody @Valid CidadeInput cidadeInput){		
		
		try {
			Cidade cidade = cidadeInputDeconvert.toDomainObject(cidadeInput);
			
			cidade = cadastroCidade.salvar(cidade);
			
			CidadeModel cidadeModel = cidadeModelConverter.toModel(cidade);
			
			ResourceUriHelper.addUriInResponseHeader(cidadeModel.getId());
			
			return cidadeModel;
		} catch (EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@CheckSecurity.Cidades.PodeEditar
	@PutMapping("/{cidadeId}")
	public CidadeModel atualizar(@ApiParam(value = "ID de uma cidade", example = "1", required = true)  @PathVariable Long cidadeId,
			                     @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados") @RequestBody @Valid CidadeInput cidadeInput){
		Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);
	    
	    //BeanUtils.copyProperties(cidade, cidadeAtual, "id");
		
		cidadeInputDeconvert.copyToDomainObject(cidadeInput, cidadeAtual);
		
	    try {
	    	return cidadeModelConverter.toModel(cadastroCidade.salvar(cidadeAtual));
	    } catch (EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	    
	}

	@CheckSecurity.Cidades.PodeEditar
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@ApiParam(value = "ID de uma cidade", example = "1", required = true) @PathVariable Long cidadeId) {
	    cadastroCidade.excluir(cidadeId);	
	}
	
	
}
