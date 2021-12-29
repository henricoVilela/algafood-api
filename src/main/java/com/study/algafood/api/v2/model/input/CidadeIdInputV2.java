package com.study.algafood.api.v2.model.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeIdInputV2 {
	
	@ApiModelProperty(example = "1", required = true)
	@NotNull
	private Long id;
}
