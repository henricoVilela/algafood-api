package com.study.algafood.api.v1.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.study.algafood.api.v1.model.input.UsuarioInput;
import com.study.algafood.domain.model.Usuario;

@Component
public class UsuarioInputDeconvert {
	@Autowired
    private ModelMapper modelMapper;
    
    public Usuario toDomainObject(UsuarioInput usuarioInput) {
        return modelMapper.map(usuarioInput, Usuario.class);
    }
    
    public void copyToDomainObject(UsuarioInput usuarioInput, Usuario usuario) {
        modelMapper.map(usuarioInput, usuario);
    }   
}
