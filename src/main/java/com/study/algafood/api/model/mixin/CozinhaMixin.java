package com.study.algafood.api.model.mixin;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.study.algafood.model.Restaurante;

public abstract class CozinhaMixin {
	
	 @JsonIgnore
	 private List<Restaurante> restaurantes;
}
