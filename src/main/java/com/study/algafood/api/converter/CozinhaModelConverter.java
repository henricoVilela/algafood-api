package com.study.algafood.api.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.study.algafood.api.CreatorLinks;
import com.study.algafood.api.controller.CozinhaController;
import com.study.algafood.api.model.CozinhaModel;
import com.study.algafood.domain.model.Cozinha;

@Component
public class CozinhaModelConverter extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModel>{
	
    @Autowired
    private ModelMapper modelMapper;
    
	@Autowired
	private CreatorLinks linkService;
    
	public CozinhaModelConverter() {
		super(CozinhaController.class, CozinhaModel.class);
	}
    
	@Override
	public CozinhaModel toModel(Cozinha cozinha) {
		CozinhaModel cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
		modelMapper.map(cozinha, cozinhaModel);
	
	    cozinhaModel.add(linkService.linkToCozinhas("cozinhas"));
		
		return cozinhaModel;
	}
    
    public List<CozinhaModel> toCollectionModel(List<Cozinha> cozinhas) {
        return cozinhas.stream()
                .map(cozinha -> toModel(cozinha))
                .collect(Collectors.toList());
    } 
}
