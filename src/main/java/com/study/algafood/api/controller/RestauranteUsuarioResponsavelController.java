package com.study.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.study.algafood.api.CreatorLinks;
import com.study.algafood.api.converter.UsuarioModelConverter;
import com.study.algafood.api.model.UsuarioModel;
import com.study.algafood.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.study.algafood.domain.model.Restaurante;
import com.study.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi{

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;
    
    @Autowired
    private UsuarioModelConverter usuarioModelConverter;
    
    @Autowired
	private CreatorLinks linkService;
    
    @GetMapping
    public CollectionModel<UsuarioModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
        
        return usuarioModelConverter.toCollectionModel(restaurante.getResponsaveis())
        		.removeLinks()
				.add(linkService.linkToRestauranteResponsaveis(restauranteId));
    }
    
    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestaurante.desassociarResponsavel(restauranteId, usuarioId);
        
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestaurante.associarResponsavel(restauranteId, usuarioId);
        
        return ResponseEntity.noContent().build();
    }
}