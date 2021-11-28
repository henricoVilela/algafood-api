package com.study.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.study.algafood.api.converter.GrupoInputDeconvert;
import com.study.algafood.api.converter.GrupoModelConverter;
import com.study.algafood.api.model.GrupoModel;
import com.study.algafood.api.model.input.GrupoInput;
import com.study.algafood.api.openapi.controller.GrupoControllerOpenApi;
import com.study.algafood.domain.model.Grupo;
import com.study.algafood.domain.repository.GrupoRepository;
import com.study.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(path="/grupos",produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi{

    @Autowired
    private GrupoRepository grupoRepository;
    
    @Autowired
    private CadastroGrupoService cadastroGrupo;
    
    @Autowired
    private GrupoModelConverter grupoModelConverter;
    
    @Autowired
    private GrupoInputDeconvert grupoInputDeconvert;
    
    @GetMapping
    public List<GrupoModel> listar() {
        List<Grupo> todosGrupos = grupoRepository.findAll();
        
        return grupoModelConverter.toCollectionModel(todosGrupos);
    }
    
    @GetMapping("/{grupoId}")
    public GrupoModel buscar(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
        
        return grupoModelConverter.toModel(grupo);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoInputDeconvert.toDomainObject(grupoInput);
        
        grupo = cadastroGrupo.salvar(grupo);
        
        return grupoModelConverter.toModel(grupo);
    }
    
    @PutMapping("/{grupoId}")
    public GrupoModel atualizar(@PathVariable Long grupoId,
            @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupoAtual = cadastroGrupo.buscarOuFalhar(grupoId);
        
        grupoInputDeconvert.copyToDomainObject(grupoInput, grupoAtual);
        
        grupoAtual = cadastroGrupo.salvar(grupoAtual);
        
        return grupoModelConverter.toModel(grupoAtual);
    }
    
    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long grupoId) {
        cadastroGrupo.excluir(grupoId);	
    }   
} 