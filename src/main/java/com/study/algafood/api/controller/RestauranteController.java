package com.study.algafood.api.controller;


import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.algafood.api.converter.RestauranteInputDeconvert;
import com.study.algafood.api.converter.RestauranteModelConverter;
import com.study.algafood.api.exception.CozinhaNaoEncontradaException;
import com.study.algafood.api.exception.EntidadeNaoEncontradaException;
import com.study.algafood.api.exception.NegocioException;
import com.study.algafood.api.exception.ValidacaoException;
import com.study.algafood.api.model.CozinhaModel;
import com.study.algafood.api.model.RestauranteModel;
import com.study.algafood.api.model.input.CozinhaIdInput;
import com.study.algafood.api.model.input.RestauranteInput;
import com.study.algafood.api.service.CadastroRestauranteService;
import com.study.algafood.model.Cozinha;
import com.study.algafood.model.Restaurante;
import com.study.algafood.repository.RestauranteRepository;



@RestController //ja contem o @ResponseBody responsavel por adicionar os resultados dos metodos no corpo da requisicao
@RequestMapping(value="/restaurantes")
public class RestauranteController {
	
	@Autowired
    private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private SmartValidator validator;
	
	@Autowired
	private RestauranteModelConverter restauranteModelConverter;
	
	@Autowired RestauranteInputDeconvert restauranteInputDeconvert;
	
	@GetMapping
	public List<RestauranteModel> listar() {
		return restauranteModelConverter.toCollectionModel(restauranteRepository.findAll());
	}
	
	@GetMapping("/{restauranteId}")
	public RestauranteModel buscar(@PathVariable Long restauranteId) {
		
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		
		return restauranteModelConverter.toModel(restaurante);

	}
	
	@GetMapping("/por-taxa-frete")
	public List<RestauranteModel> listar(@RequestParam("taxaInicial") BigDecimal taxaInicial, @RequestParam("taxaFinal") BigDecimal taxaFinal) {
		return restauranteModelConverter.toCollectionModel(restauranteRepository.findByTaxaFreteBetween(taxaInicial,taxaFinal));
	}
	
	@GetMapping("/por-nome-and-taxa-frete")
	public List<RestauranteModel> listarPorNome(@RequestParam(value="nome", required = false) String nome, @RequestParam(value="taxaInicial",required = false) BigDecimal taxaInicial, @RequestParam(value="taxaFinal",required = false) BigDecimal taxaFinal){
		return restauranteModelConverter.toCollectionModel(restauranteRepository.find(nome, taxaInicial, taxaFinal));
	}
	
	@GetMapping("/com-frete-gratis")
	public List<RestauranteModel> restauranteComFreteGratis(String nome){
		
		return restauranteModelConverter.toCollectionModel(restauranteRepository.findComFreteGratis(nome));
	}
	
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
		}
    }
	
	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long restauranteId){
		cadastroRestaurante.ativar(restauranteId);
	}
	
	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long restauranteId){
		cadastroRestaurante.inativar(restauranteId);
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
	
	
}
