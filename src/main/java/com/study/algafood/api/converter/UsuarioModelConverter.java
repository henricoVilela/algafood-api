package com.study.algafood.api.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.study.algafood.api.model.UsuarioModel;
import com.study.algafood.model.Usuario;

@Component
public class UsuarioModelConverter {
	
	@Autowired
    private ModelMapper modelMapper;
    
    public UsuarioModel toModel(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioModel.class);
    }
    
    public List<UsuarioModel> toCollectionModel(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(usuario -> toModel(usuario))
                .collect(Collectors.toList());
    } 
}
