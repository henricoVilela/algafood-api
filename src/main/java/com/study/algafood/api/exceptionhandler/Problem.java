package com.study.algafood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel("Problema")
@JsonInclude(Include.NON_EMPTY)
@Getter
@Builder
public class Problem {

	@ApiModelProperty(example = "400")
	private Integer status;
	
	@ApiModelProperty(example = "http://localhost:8080/dados-invalidos")
	private String type;
	
	@ApiModelProperty(example = "Dados Invalidos")
	private String title;
	
	private String detail;
	
	private String userMessage;
	
	@ApiModelProperty(example = "2021-11-28T18:09:12Z")
	private OffsetDateTime timestamp;
	
	@ApiModelProperty(value = "Lista de objetos ou campos que acarrateram o problema.")
	private List<Objects> objects;
	
	@ApiModel("ObjetoProblema")
	@Getter
	@Builder
	public static class Objects{
		private String name;
		private String userMessage;
	}
	
}
