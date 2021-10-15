package com.study.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.study.algafood.AlgafoodApiApplication;
import com.study.algafood.domain.model.Cozinha;
import com.study.algafood.domain.model.Restaurante;
import com.study.algafood.domain.repository.CozinhaRepository;
import com.study.algafood.domain.repository.RestauranteRepository;

public class ConsultaCozinhaMain {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CozinhaRepository cadastroCozinha = applicationContext.getBean(CozinhaRepository.class);
		Cozinha cozinha;
		Long id = Long.parseLong("2");
		//List<Cozinha> cozinhas = cadastroCozinha.listar();
		
		/*cozinhas.forEach(e -> {
			System.out.println("nome: "+e.getNome());
		});*/
		
		//cozinha = cadastroCozinha.buscar(id);
		//System.out.println("find - nome: "+cozinha.getNome());
		
		RestauranteRepository restautantes  = applicationContext.getBean(RestauranteRepository.class);
		List<Restaurante> todosRestaurantes = restautantes.findAll();
		
		todosRestaurantes.forEach(e -> {
			System.out.println("nome - rest: "+e.getNome());
		});
	}
}
