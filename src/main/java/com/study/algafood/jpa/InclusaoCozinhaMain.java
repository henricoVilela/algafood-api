package com.study.algafood.jpa;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.study.algafood.AlgafoodApiApplication;
import com.study.algafood.model.Cozinha;
import com.study.algafood.model.Restaurante;
import com.study.algafood.repository.CozinhaRepository;
import com.study.algafood.repository.RestauranteRepository;

public class InclusaoCozinhaMain {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CozinhaRepository cadastroCozinha = applicationContext.getBean(CozinhaRepository.class);
		
		Cozinha cozinha = new Cozinha();
		cozinha.setId(1L);
		cozinha.setNome("Editada");
		
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Brasileira");
		
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Japonesa");
		
		//cozinha1 = cadastroCozinha.salvar(cozinha1);
		//cozinha2 = cadastroCozinha.salvar(cozinha2);
		//cadastroCozinha.salvar(cozinha);
		
		//cadastroCozinha.remover(cozinha1);
		
		//List<Cozinha> cozinhas = cadastroCozinha.listar();
		
		/*cozinhas.forEach(e -> {
			System.out.println("nome: "+e.getNome());
		});*/
		
		Restaurante restaurante1 = new Restaurante();		
		BigDecimal big1 = new BigDecimal("19.1");
		restaurante1.setNome("RestMika");
		restaurante1.setTaxaFrete(big1);
		restaurante1.setCozinha(cozinha2);
		
		Restaurante restaurante2 = new Restaurante();		
		BigDecimal big2 = new BigDecimal("34.1");
		restaurante2.setNome("RestJuju");
		restaurante2.setTaxaFrete(big2);
		restaurante2.setCozinha(cozinha2);
		
		
		RestauranteRepository restautantes  = applicationContext.getBean(RestauranteRepository.class);
		
		/*restaurante1 = restautantes.adicionar(restaurante1);
		restautantes.adicionar(restaurante2);
		restautantes.remover(restaurante1);
		
		List<Restaurante> todosRestaurantes = restautantes.listar();
		
		todosRestaurantes.forEach(e -> {
			System.out.println("nome - rest: "+e.getNome()+" | Cozinha -> "+e.getCozinha().getNome());
		});*/
	}
}
