package com.study.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.study.algafood.api.model.CozinhasXmlWrapper;
import com.study.algafood.api.service.CadastroCozinhaService;
import com.study.algafood.model.Cozinha;
import com.study.algafood.repository.CozinhaRepository;



@RestController //ja contem o @ResponseBody responsavel por adicionar os resultados dos metodos no corpo da requisicao
@RequestMapping(value="/cozinhas", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class CozinhaController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	//@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) //Produces diz a estrutura de dados retornado pelo metodo para a resposta http
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE})
	public List<Cozinha> listar(){
		return cozinhaRepository.findAll();
	}
	
	//Metodo para poder customizar as respostas em xml
	@GetMapping(produces =  MediaType.APPLICATION_XML_VALUE)
	public CozinhasXmlWrapper listarXml(){
		return new CozinhasXmlWrapper(cozinhaRepository.findAll());
	}
	
	@GetMapping("/por-nome")
	public List<Cozinha> listarPorNome(@RequestParam("nome") String nome){
		return cozinhaRepository.consultarPorNome(nome);
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
	
	@GetMapping(value="{cozinhaId}")
	public Cozinha buscar(@PathVariable("cozinhaId") Long id) {
		return cadastroCozinha.buscarOuFalhar(id);   
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody Cozinha cozinha) {
		return cadastroCozinha.salvar(cozinha);
	}
	
	@PutMapping("/{cozinhaId}")
	public Cozinha atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha){
		
		Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);
		//Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(cozinhaId);
		

		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id"); //Diz que quer ignorar o campo ID pora não trocar para nulo
	
		Cozinha cozinhaSalva = cadastroCozinha.salvar(cozinhaAtual);
		
		return cozinhaSalva;

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
	
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId){
			cadastroCozinha.excluir(cozinhaId);
	}
		
}
