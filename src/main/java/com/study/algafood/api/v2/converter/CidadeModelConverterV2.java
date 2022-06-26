package com.study.algafood.api.v2.converter;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.study.algafood.api.v1.controller.CidadeController;
import com.study.algafood.api.v2.CreatorLinksV2;
import com.study.algafood.api.v2.controller.CidadeControllerV2;
import com.study.algafood.api.v2.model.CidadeModelV2;
import com.study.algafood.domain.model.Cidade;

@Component
public class CidadeModelConverterV2 extends RepresentationModelAssemblerSupport<Cidade, CidadeModelV2>{
	
	public CidadeModelConverterV2() {
		super(CidadeControllerV2.class, CidadeModelV2.class);
	}

	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
	private CreatorLinksV2 linkService;
    
	@Override
    public CidadeModelV2 toModel(Cidade cidade) {
		CidadeModelV2 cidadeModel = createModelWithId(cidade.getId(), cidade);
		
		modelMapper.map(cidade, cidadeModel);
		
	    cidadeModel.add(linkService.linkToCidades("cidades"));
		
		return cidadeModel;
    }
    
	@Override
	public CollectionModel<CidadeModelV2> toCollectionModel(Iterable<? extends Cidade> entities) {
		return super.toCollectionModel(entities)
				.add(linkTo(CidadeController.class).withSelfRel());
	}
}
