package com.study.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.algafood.api.v1.CreatorLinks;
import com.study.algafood.core.security.AlgaSecurity;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping(path = "/v1",produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

	@Autowired
	private CreatorLinks linkService;
	
	@Autowired
	private AlgaSecurity algaSecurity; 
	
	@GetMapping
	public RootEntryPointModel root() {
		var rootEntryPointModel = new RootEntryPointModel();
		
		if (algaSecurity.podeConsultarCozinhas())
			rootEntryPointModel.add(linkService.linkToCozinhas("cozinhas"));
		
		if (algaSecurity.podePesquisarPedidos())
			rootEntryPointModel.add(linkService.linkToPedidos("pedidos"));
		
		if (algaSecurity.podeConsultarRestaurantes())
			rootEntryPointModel.add(linkService.linkToRestaurantes("restaurantes"));
		
		//rootEntryPointModel.add(linkService.linkToGrupos("grupos"));
		
		if (algaSecurity.podeConsultarUsuariosGruposPermissoes())
			rootEntryPointModel.add(linkService.linkToUsuarios("usuarios"));
		
		//rootEntryPointModel.add(linkService.linkToPermissoes());
		//rootEntryPointModel.add(linkService.linkToFormasPagamento("formas-pagamento"));
		
		if (algaSecurity.podeConsultarEstados())
			rootEntryPointModel.add(linkService.linkToEstados("estados"));
		
		if (algaSecurity.podeConsultarCidades())
			rootEntryPointModel.add(linkService.linkToCidades("cidades"));
		
		return rootEntryPointModel;
	}
	
	private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
	}
	
}
