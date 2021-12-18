package com.study.algafood.api.converter;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.study.algafood.api.controller.UsuarioController;
import com.study.algafood.api.controller.UsuarioGrupoController;
import com.study.algafood.api.model.UsuarioModel;
import com.study.algafood.domain.model.Usuario;

@Component
public class UsuarioModelConverter extends RepresentationModelAssemblerSupport<Usuario, UsuarioModel>{
	
	@Autowired
    private ModelMapper modelMapper;
	
	public UsuarioModelConverter() {
        super(UsuarioController.class, UsuarioModel.class);
    }
    
    public UsuarioModel toModel(Usuario usuario) {        
    	UsuarioModel usuarioModel = createModelWithId(usuario.getId(), usuario);
	    modelMapper.map(usuario, usuarioModel);
	    
	    usuarioModel.add(linkTo(UsuarioController.class).withRel("usuarios"));
	    
	    usuarioModel.add(linkTo(methodOn(UsuarioGrupoController.class)
	            .listar(usuario.getId())).withRel("grupos-usuario"));
	    
	    return usuarioModel;
    }
    
    @Override
    public CollectionModel<UsuarioModel> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities)
            .add(linkTo(UsuarioController.class).withSelfRel());
    }  
}
