package com.study.algafood.api.v1.model.mixin;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.study.algafood.domain.model.Restaurante;

public abstract class CozinhaMixin {
	
	 @JsonIgnore
	 private List<Restaurante> restaurantes;
}
