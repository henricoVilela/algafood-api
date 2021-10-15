package com.study.algafood.domain.model.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class VendaDiaria {
	
	private Date data;
	private Long totalVendas;
	private BigDecimal totalFaturado;
}
