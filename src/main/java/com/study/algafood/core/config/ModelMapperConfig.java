package com.study.algafood.core.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper =  new ModelMapper();
		
		//Customizando um mapeamento para uma propriedade que nao tem correspondencia no model
		/*modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
		           .addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);*/
		
		return modelMapper;
	}
	
}
