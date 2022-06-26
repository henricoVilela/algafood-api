package com.study.algafood.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;

@Component
public class JacksonMixinModule extends SimpleModule{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JacksonMixinModule() {
		//setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
		//setMixInAnnotation(Cidade.class, CidadeMixin.class);
	    //setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
	}

}
