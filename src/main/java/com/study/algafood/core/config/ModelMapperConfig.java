package com.study.algafood.core.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.study.algafood.api.v1.model.EnderecoModel;
import com.study.algafood.api.v1.model.input.ItemPedidoInput;
import com.study.algafood.api.v2.model.input.CidadeInputV2;
import com.study.algafood.domain.model.Cidade;
import com.study.algafood.domain.model.Endereco;
import com.study.algafood.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper =  new ModelMapper();
		
		modelMapper.createTypeMap(CidadeInputV2.class, Cidade.class)
		.addMappings(mapper -> mapper.skip(Cidade::setId));
		
		//Customizando um mapeamento para uma propriedade que nao tem correspondencia no model
		/*modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
		           .addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);*/
		
		//Item Pedido
		modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
	    .addMappings(mapper -> mapper.skip(ItemPedido::setId));
		
		var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);
		
		enderecoToEnderecoModelTypeMap.<String>addMapping(
					enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
					(enderecoModelDest,value) -> enderecoModelDest.getCidade().setEstado(value));
		
		
		
		return modelMapper;
	}
	
}
