package com.study.algafood.api.v1.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.study.algafood.api.v1.model.FotoProdutoModel;
import com.study.algafood.domain.model.FotoProduto;

@Component
public class FotoProdutoModelConverter {
	@Autowired
	private ModelMapper modelMapper;
	
	public FotoProdutoModel toModel(FotoProduto foto) {
		return modelMapper.map(foto, FotoProdutoModel.class);
	}
}
