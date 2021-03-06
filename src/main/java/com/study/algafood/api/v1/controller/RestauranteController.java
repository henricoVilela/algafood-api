package com.study.algafood.api.v1.controller;


import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
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

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.algafood.api.v1.converter.RestauranteInputDeconvert;
import com.study.algafood.api.v1.converter.RestauranteModelConverter;
import com.study.algafood.api.v1.model.RestauranteModel;
import com.study.algafood.api.v1.model.input.RestauranteInput;
import com.study.algafood.api.v1.model.view.RestauranteView;
import com.study.algafood.api.v1.openapi.controller.RestauranteControllerOpenApi;
import com.study.algafood.core.security.CheckSecurity;
import com.study.algafood.domain.exception.CidadeNaoEncontradaException;
import com.study.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.study.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.study.algafood.domain.exception.NegocioException;
import com.study.algafood.domain.exception.RestauranteNaoEncontradaException;
import com.study.algafood.domain.exception.ValidacaoException;
import com.study.algafood.domain.model.Restaurante;
import com.study.algafood.domain.repository.RestauranteRepository;
import com.study.algafood.domain.service.CadastroRestauranteService;


//@CrossOrigin(origins = "http://localhost:8000")
@RestController //ja contem o @ResponseBody responsavel por adicionar os resultados dos metodos no corpo da requisicao
@RequestMapping(value="/v1/restaurantes")
public class RestauranteController implements RestauranteControllerOpenApi {
	
	@Autowired
    private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private SmartValidator validator;
	
	@Autowired
	private RestauranteModelConverter restauranteModelConverter;
	
	@Autowired RestauranteInputDeconvert restauranteInputDeconvert;
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@JsonView(RestauranteView.Resumo.class)
	@GetMapping
	public List<RestauranteModel> listar() {
		return restauranteModelConverter.toCollectionModel(restauranteRepository.findAll());
	}

	@CheckSecurity.Restaurantes.PodeConsultar
	@JsonView(RestauranteView.ApenasNome.class)
	@GetMapping(params = "projecao=apenas-nome")
	public List<RestauranteModel> listarApenasNomes() {
		return listar();
	}
	
//	@GetMapping
//	public MappingJacksonValue listar(@RequestParam(required = false) String projecao) {
//		List<Restaurante> restaurantes = restauranteRepository.findAll();
//		List<RestauranteModel> restaurantesModel = restauranteModelAssembler.toCollectionModel(restaurantes);
//		
//		MappingJacksonValue restaurantesWrapper = new MappingJacksonValue(restaurantesModel);
//		
//		restaurantesWrapper.setSerializationView(RestauranteView.Resumo.class);
//		
//		if ("apenas-nome".equals(projecao)) {
//			restaurantesWrapper.setSerializationView(RestauranteView.ApenasNome.class);
//		} else if ("completo".equals(projecao)) {
//			restaurantesWrapper.setSerializationView(null);
//		}
//		
//		return restaurantesWrapper;
//	}
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping("/{restauranteId}")
	public RestauranteModel buscar(@PathVariable Long restauranteId) {
		
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		
		return restauranteModelConverter.toModel(restaurante);

	}
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping("/por-taxa-frete")
	public List<RestauranteModel> listar(@RequestParam("taxaInicial") BigDecimal taxaInicial, @RequestParam("taxaFinal") BigDecimal taxaFinal) {
		return restauranteModelConverter.toCollectionModel(restauranteRepository.findByTaxaFreteBetween(taxaInicial,taxaFinal));
	}
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping("/por-nome-and-taxa-frete")
	public List<RestauranteModel> listarPorNome(@RequestParam(value="nome", required = false) String nome, @RequestParam(value="taxaInicial",required = false) BigDecimal taxaInicial, @RequestParam(value="taxaFinal",required = false) BigDecimal taxaFinal){
		return restauranteModelConverter.toCollectionModel(restauranteRepository.find(nome, taxaInicial, taxaFinal));
	}
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping("/com-frete-gratis")
	public List<RestauranteModel> restauranteComFreteGratis(String nome){
		
		return restauranteModelConverter.toCollectionModel(restauranteRepository.findComFreteGratis(nome));
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput){
		
		try {
			
			Restaurante restaurante = restauranteInputDeconvert.toDomainModel(restauranteInput);
			
	        return restauranteModelConverter.toModel(cadastroRestaurante.salvar(restaurante));
	    } catch (EntidadeNaoEncontradaException e) {
	        throw new NegocioException(e.getMessage());
	    }
		
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PutMapping("/{restauranteId}")
    public RestauranteModel atualizar(@PathVariable Long restauranteId, @RequestBody @Valid RestauranteInput restauranteInput) {
		try {
			//Restaurante restaurante = restauranteInputDeconvert.toDomainModel(restauranteInput);
			
			Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
			
			restauranteInputDeconvert.copyToDomainObject(restauranteInput, restauranteAtual);
			
			/*BeanUtils.copyProperties(restaurante, restauranteAtual, 
					"id", "formasPagamento", "endereco", "dataCadastro", "produtos");*/

			return restauranteModelConverter.toModel(cadastroRestaurante.salvar(restauranteAtual));
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		} catch (CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
    }
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> ativar(@PathVariable Long restauranteId){
		cadastroRestaurante.ativar(restauranteId);
		
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> inativar(@PathVariable Long restauranteId){
		cadastroRestaurante.inativar(restauranteId);
		
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> abrir(@PathVariable Long restauranteId) {
	    cadastroRestaurante.abrir(restauranteId);
	    
	    return ResponseEntity.noContent().build();
	}

	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> fechar(@PathVariable Long restauranteId) {
	    cadastroRestaurante.fechar(restauranteId);
	    
	    return ResponseEntity.noContent().build();
	}  
	
	/*@PatchMapping("/{restauranteId}")
	public RestauranteModel atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos, HttpServletRequest request){
		
		Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
	    
	    merge(campos, restauranteAtual, request);
	    validate(restauranteAtual,"restaurante");
	    
	    return atualizar(restauranteId, restauranteAtual);
		
	}*/

	private void validate(Restaurante restauranteAtual, String objectName) {
		
		BeanPropertyBindingResult bindResult = new BeanPropertyBindingResult(restauranteAtual, objectName);
		
		validator.validate(restauranteAtual, bindResult);
		
		if(bindResult.hasErrors()) {
			throw new ValidacaoException(bindResult);
		}
	}

	private void merge(Map<String, Object> campos, Restaurante restauranteDestino, HttpServletRequest request) {
		
		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			
			Restaurante restauranteOrigem = objectMapper.convertValue(campos, Restaurante.class);
			
			campos.forEach((nomePropriedade, valorPropriedade) -> {
				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
				field.setAccessible(true);
				
				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
				
				ReflectionUtils.setField(field, restauranteDestino, novoValor);
			});
		} catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
		}
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> ativarMultiplos(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestaurante.ativar(restauranteIds);
		} catch (RestauranteNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
		
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> inativarMultiplos(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestaurante.inativar(restauranteIds);
		} catch (RestauranteNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
		
		return ResponseEntity.noContent().build();
	}
	
	
}
