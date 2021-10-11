package com.study.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.study.algafood.api.converter.ProdutoInputDeconvert;
import com.study.algafood.api.converter.ProdutoModelConverter;
import com.study.algafood.api.model.ProdutoModel;
import com.study.algafood.api.model.input.ProdutoInput;
import com.study.algafood.api.service.CadastroProdutoService;
import com.study.algafood.api.service.CadastroRestauranteService;
import com.study.algafood.model.Produto;
import com.study.algafood.model.Restaurante;
import com.study.algafood.repository.ProdutoRepository;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Autowired
    private CadastroProdutoService cadastroProduto;
    
    @Autowired
    private CadastroRestauranteService cadastroRestaurante;
    
    @Autowired
    private ProdutoModelConverter produtoModelConverter;
    
    @Autowired
    private ProdutoInputDeconvert produtoInputDeconvert;
    
    @GetMapping
    public List<ProdutoModel> listar(@PathVariable Long restauranteId,@RequestParam(required = false) boolean incluirInativos) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
        
        List<Produto> todosProdutos = produtoRepository.findByRestauranteAndAtivo(restaurante, true);
        
        if (incluirInativos) {
			todosProdutos = produtoRepository.findByRestaurante(restaurante);
		} else {
			todosProdutos = produtoRepository.findByRestauranteAndAtivo(restaurante, true);
		}
        
        return produtoModelConverter.toCollectionModel(todosProdutos);
    }
    
    @GetMapping("/{produtoId}")
    public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Produto produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);
        
        return produtoModelConverter.toModel(produto);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel adicionar(@PathVariable Long restauranteId,
            @RequestBody @Valid ProdutoInput produtoInput) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
        
        Produto produto = produtoInputDeconvert.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);
        
        produto = cadastroProduto.salvar(produto);
        
        return produtoModelConverter.toModel(produto);
    }
    
    @PutMapping("/{produtoId}")
    public ProdutoModel atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
            @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produtoAtual = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);
        
        produtoInputDeconvert.copyToDomainObject(produtoInput, produtoAtual);
        
        produtoAtual = cadastroProduto.salvar(produtoAtual);
        
        return produtoModelConverter.toModel(produtoAtual);
    }   
} 
