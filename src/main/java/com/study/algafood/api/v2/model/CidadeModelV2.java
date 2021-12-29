package com.study.algafood.api.v2.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//@ApiModel(description = "Representa uma cidade")
@ApiModel("CidadeModel")
@Relation(collectionRelation = "cidades")
@Setter
@Getter
public class CidadeModelV2 extends RepresentationModel<CidadeModelV2>{
	
	@ApiModelProperty(example = "1")
    private Long id;
	
	@ApiModelProperty(example = "Campinas")
    private String nome;
	
	private Long idEstado;
	private String nomeEstado;
}