package com.study.algafood.api.v1.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.study.algafood.api.v1.model.input.RestauranteInput;
import com.study.algafood.domain.model.Cidade;
import com.study.algafood.domain.model.Cozinha;
import com.study.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputDeconvert {
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	public Restaurante toDomainModel(RestauranteInput restauranteInput) {
		
		return modelMapper.map(restauranteInput, Restaurante.class);
	}
	
	public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
		
		//Instacia nova cozinha para nao da erro quando alterar a cozinha no put
		restaurante.setCozinha(new Cozinha());
		
		if(restaurante.getEndereco() != null) {
			restaurante.getEndereco().setCidade(new Cidade());
		}
		
		modelMapper.map(restauranteInput, restaurante);
	}
}
