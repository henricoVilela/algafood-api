package com.study.algafood.api.v1.openapi.model;

import java.math.BigDecimal;

import com.study.algafood.api.v1.model.CozinhaModel;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;

@ApiOperation("RestauranteBasicoModel")
@Getter
@Setter
public class RestauranteBasicoModelOpenApi {
	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Steak house")
	private String nome;
	
	@ApiModelProperty(example = "12.00")
	private BigDecimal taxaFrete;
	
	private CozinhaModel cozinha;
	
	@ApiModelProperty(example = "Aberto")
	private Boolean aberto;
}
