package com.study.algafood.api.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.study.algafood.api.model.input.RestauranteInput;
import com.study.algafood.model.Cozinha;
import com.study.algafood.model.Restaurante;

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
		modelMapper.map(restauranteInput, restaurante);
	}
}
