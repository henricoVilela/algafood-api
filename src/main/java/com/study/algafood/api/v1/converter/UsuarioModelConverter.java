package com.study.algafood.api.v1.converter;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.study.algafood.api.v1.CreatorLinks;
import com.study.algafood.api.v1.controller.UsuarioController;
import com.study.algafood.api.v1.model.UsuarioModel;
import com.study.algafood.core.security.AlgaSecurity;
import com.study.algafood.domain.model.Usuario;

@Component
public class UsuarioModelConverter extends RepresentationModelAssemblerSupport<Usuario, UsuarioModel>{
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
	private CreatorLinks linkService;
	
	@Autowired
	private AlgaSecurity algaSecurity;    
	
	public UsuarioModelConverter() {
        super(UsuarioController.class, UsuarioModel.class);
    }
    
    public UsuarioModel toModel(Usuario usuario) {        
    	UsuarioModel usuarioModel = createModelWithId(usuario.getId(), usuario);
	    modelMapper.map(usuario, usuarioModel);
	    
	    if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
		    usuarioModel.add(linkService.linkToUsuarios("usuarios"));
		    usuarioModel.add(linkService.linkToGruposUsuario(usuario.getId(), "grupos-usuario"));
	    }
	    
	    return usuarioModel;
    }
    
    @Override
    public CollectionModel<UsuarioModel> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities)
            .add(linkTo(UsuarioController.class).withSelfRel());
    }  
}
