package com.study.algafood.api.v1.model.input;

import com.sun.istack.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FormaPagamentoIdInput {
	@ApiModelProperty(example = "1", required = true)
    @NotNull
    private Long id;            
}  