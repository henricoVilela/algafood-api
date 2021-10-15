package com.study.algafood.domain.service;

import java.util.List;

import com.study.algafood.domain.filter.VendaDiariaFilter;
import com.study.algafood.domain.model.dto.VendaDiaria;

public interface VendaQueryService {
	
	List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter,String timeOffset);
}
