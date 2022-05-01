package com.study.algafood.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.study.algafood.api.v1.converter.CozinhaInputDeconvert;
import com.study.algafood.api.v1.converter.CozinhaModelConverter;
import com.study.algafood.api.v1.model.CozinhaModel;
import com.study.algafood.api.v1.model.input.CozinhaInput;
import com.study.algafood.api.v1.openapi.controller.CozinhaControllerOpenApi;
import com.study.algafood.core.security.CheckSecurity;
import com.study.algafood.domain.model.Cozinha;
import com.study.algafood.domain.repository.CozinhaRepository;
import com.study.algafood.domain.service.CadastroCozinhaService;



@RestController //ja contem o @ResponseBody responsavel por adicionar os resultados dos metodos no corpo da requisicao
@RequestMapping(value="/v1/cozinhas", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class CozinhaController implements CozinhaControllerOpenApi{
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CozinhaModelConverter cozinhaModelConverter;
	
	@Autowired
	private CozinhaInputDeconvert cozinhaInputDeconvert;
	
	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;
	
	//@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) //Produces diz a estrutura de dados retornado pelo metodo para a resposta http
	//@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE})
	@PreAuthorize("isAuthenticated()")
	@GetMapping
	public PagedModel<CozinhaModel> listar(@PageableDefault(size = 15) Pageable pageable){
		Page<Cozinha> pageCozinha = cozinhaRepository.findAll(pageable);
		
		PagedModel<CozinhaModel> cozinhaPageModel = pagedResourcesAssembler.toModel(pageCozinha, cozinhaModelConverter);
		
		return cozinhaPageModel;
	}
	
	//Metodo para poder customizar as respostas em xml
	/*@GetMapping(produces =  MediaType.APPLICATION_XML_VALUE)
	public CozinhasXmlWrapper listarXml(){
		return new CozinhasXmlWrapper(cozinhaRepository.findAll());
	}*/
	
	@CheckSecurity.Cozinhas.PodeConsultar
	@GetMapping("/por-nome")
	public List<CozinhaModel> listarPorNome(@RequestParam("nome") String nome){
		return cozinhaModelConverter.toCollectionModel(cozinhaRepository.consultarPorNome(nome));
	}
	
	/*@GetMapping(value="{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable("cozinhaId") Long id) {
		Optional<Cozinha> cozinha = cozinhaRepository.findById(id);
	    
		if (cozinha.isPresent()) {
			//return ResponseEntity.status(HttpStatus.OK).body(cozinha);
			
			// faz a mesma coisa da solucao acima
			return ResponseEntity.ok(cozinha.get()); 
		}
		
		return ResponseEntity.notFound().build();
		
	}*/
	
	@CheckSecurity.Cozinhas.PodeConsultar
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value="{cozinhaId}")
	public CozinhaModel buscar(@PathVariable("cozinhaId") Long id) {
		return cozinhaModelConverter.toModel(cadastroCozinha.buscarOuFalhar(id));   
	}
	
	@CheckSecurity.Cozinhas.PodeEditar
	@PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinha = cozinhaInputDeconvert.toDomainObject(cozinhaInput);
		return cozinhaModelConverter.toModel(cadastroCozinha.salvar(cozinha));
	}
	
	@CheckSecurity.Cozinhas.PodeEditar
	@PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
	@PutMapping("/{cozinhaId}")
	public CozinhaModel atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInput cozinhaInput){
		
		Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);
		//Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(cozinhaId);
		

		//BeanUtils.copyProperties(cozinha, cozinhaAtual, "id"); //Diz que quer ignorar o campo ID pora não trocar para nulo
		
		cozinhaInputDeconvert.copyToDomainObject(cozinhaInput, cozinhaAtual);
		
		Cozinha cozinhaSalva = cadastroCozinha.salvar(cozinhaAtual);
		
		return cozinhaModelConverter.toModel(cozinhaSalva);

	}
	
	/*@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> remover(@PathVariable Long cozinhaId){
		
		try {
			cadastroCozinha.excluir(cozinhaId);
			return ResponseEntity.noContent().build();

		}catch(EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
		
		
		
	}*/
	
	@PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId){
			cadastroCozinha.excluir(cozinhaId);
	}
		
}
