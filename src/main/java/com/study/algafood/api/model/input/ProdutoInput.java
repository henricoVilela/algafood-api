package com.study.algafood.api.model.input;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProdutoInput {

    @NotBlank
    private String nome;
    
    @NotBlank
    private String descricao;
    
    @NotNull
    @PositiveOrZero
    private BigDecimal preco;
    
    @NotNull
    private Boolean ativo;
    
}  