package com.study.algafood.core.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.study.algafood.api.model.EnderecoModel;
import com.study.algafood.api.model.input.ItemPedidoInput;
import com.study.algafood.model.Endereco;
import com.study.algafood.model.ItemPedido;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper =  new ModelMapper();
		
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
