package com.study.algafood.api.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonView;
import com.study.algafood.api.model.view.RestauranteView;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteModel {
	@ApiModelProperty(example = "1")
	@JsonView({RestauranteView.Resumo.class,RestauranteView.ApenasNome.class})
	private Long id;
	
	@ApiModelProperty(example = "Thai Gourmet")
	@JsonView({RestauranteView.Resumo.class,RestauranteView.ApenasNome.class})
	private String nome;

	@ApiModelProperty(example = "12.00")
	@JsonView(RestauranteView.Resumo.class)
	private BigDecimal taxaFrete;
	
	@JsonView(RestauranteView.Resumo.class)
	private CozinhaModel cozinha;
	
	private Boolean ativo;
	private EnderecoModel endereco;
	
	@JsonView(RestauranteView.Resumo.class)
	private Boolean aberto;
	//private BigDecimal precoFrete;

}
