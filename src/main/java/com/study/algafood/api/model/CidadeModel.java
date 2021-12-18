package com.study.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//@ApiModel(description = "Representa uma cidade")
@Relation(collectionRelation = "cidades")
@Setter
@Getter
public class CidadeModel extends RepresentationModel<CidadeModel>{
	
	@ApiModelProperty(example = "1")
    private Long id;
	
	@ApiModelProperty(example = "Campinas")
    private String nome;

    private EstadoModel estado;
}