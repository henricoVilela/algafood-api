package com.study.algafood.api.v1.converter;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.study.algafood.api.v1.CreatorLinks;
import com.study.algafood.api.v1.controller.EstadoController;
import com.study.algafood.api.v1.model.EstadoModel;
import com.study.algafood.domain.model.Estado;

@Component
public class EstadoModelConverter extends RepresentationModelAssemblerSupport<Estado, EstadoModel>{
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
	private CreatorLinks linkService;
	
    public EstadoModelConverter() {
        super(EstadoController.class, EstadoModel.class);
    }
	
    public EstadoModel toModel(Estado estado) {
        EstadoModel estadoModel = createModelWithId(estado.getId(), estado);
        modelMapper.map(estado, estadoModel);
        
        estadoModel.add(linkService.linkToEstados("estados"));
        
        return estadoModel;    
    }
    
    @Override
    public CollectionModel<EstadoModel> toCollectionModel(Iterable<? extends Estado> entities) {
        return super.toCollectionModel(entities)
            .add(linkTo(EstadoController.class).withSelfRel());
    } 
}
