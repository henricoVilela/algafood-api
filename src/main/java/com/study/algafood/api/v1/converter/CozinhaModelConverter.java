package com.study.algafood.api.v1.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.study.algafood.api.v1.CreatorLinks;
import com.study.algafood.api.v1.controller.CozinhaController;
import com.study.algafood.api.v1.model.CozinhaModel;
import com.study.algafood.core.security.AlgaSecurity;
import com.study.algafood.domain.model.Cozinha;

@Component
public class CozinhaModelConverter extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModel>{
	
    @Autowired
    private ModelMapper modelMapper;
    
	@Autowired
	private CreatorLinks linkService;
	
	@Autowired
	private AlgaSecurity algaSecurity;    
    
	public CozinhaModelConverter() {
		super(CozinhaController.class, CozinhaModel.class);
	}
    
	@Override
	public CozinhaModel toModel(Cozinha cozinha) {
		CozinhaModel cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
		modelMapper.map(cozinha, cozinhaModel);
	
		if (algaSecurity.podeConsultarCozinhas())
			cozinhaModel.add(linkService.linkToCozinhas("cozinhas"));
		
		return cozinhaModel;
	}
    
    public List<CozinhaModel> toCollectionModel(List<Cozinha> cozinhas) {
        return cozinhas.stream()
                .map(cozinha -> toModel(cozinha))
                .collect(Collectors.toList());
    } 
}
