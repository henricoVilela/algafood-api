package com.study.algafood.api.v1.converter;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.study.algafood.api.v1.CreatorLinks;
import com.study.algafood.api.v1.controller.CidadeController;
import com.study.algafood.api.v1.model.CidadeModel;
import com.study.algafood.core.security.AlgaSecurity;
import com.study.algafood.domain.model.Cidade;

@Component
public class CidadeModelConverter extends RepresentationModelAssemblerSupport<Cidade, CidadeModel>{
	
	public CidadeModelConverter() {
		super(CidadeController.class, CidadeModel.class);
	}

	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
	private CreatorLinks linkService;
	
	@Autowired
	private AlgaSecurity algaSecurity; 
    
	@Override
    public CidadeModel toModel(Cidade cidade) {
		CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);
		
		modelMapper.map(cidade, cidadeModel);
		
		if (algaSecurity.podeConsultarCidades())
			cidadeModel.add(linkService.linkToCidades("cidades"));
	    
		if (algaSecurity.podeConsultarEstados())
			cidadeModel.getEstado().add(linkService.linkToEstado(cidadeModel.getEstado().getId()));
		
		return cidadeModel;
    }
    
	@Override
	public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {

		CollectionModel<CidadeModel> collectionModel = super.toCollectionModel(entities);
	    
	    if (algaSecurity.podeConsultarCidades()) {
	        collectionModel.add(linkTo(CidadeController.class).withSelfRel());
	    }
	    
	    return collectionModel;
	}
}
