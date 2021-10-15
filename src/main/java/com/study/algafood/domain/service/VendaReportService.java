package com.study.algafood.domain.service;

import com.study.algafood.domain.filter.VendaDiariaFilter;

public interface VendaReportService {
	byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
