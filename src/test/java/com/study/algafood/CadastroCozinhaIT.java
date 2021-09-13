package com.study.algafood;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.annotation.security.RunAs;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.study.algafood.api.exception.CozinhaNaoEncontradaException;
import com.study.algafood.api.exception.EntidadeEmUsoException;
import com.study.algafood.api.service.CadastroCozinhaService;
import com.study.algafood.model.Cozinha;

@RunAs("com.study")
@SpringBootTest
public class CadastroCozinhaIT {
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Test
	public void testarCadastroCozinhaComSucesso() {
		//Cenário
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");
		
		//Acao
		novaCozinha = cadastroCozinha.salvar(novaCozinha);
		
		//Validacao
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}
	
	@Test
	public void testarCadastroCozinhaSemNome() {
			
		Exception exception = assertThrows(ConstraintViolationException.class, () -> {
			Cozinha novaCozinha = new Cozinha();
			novaCozinha.setNome(null);
			novaCozinha = cadastroCozinha.salvar(novaCozinha);
	    });


	}
	
	@Test
    public void deveFalhar_QuandoExcluirCozinhaEmUso() {
        
        assertThrows(EntidadeEmUsoException.class, () -> {
        	cadastroCozinha.excluir(1L);
	    });
    }
	
	@Test
    public void deveFalhar_QuandoExcluirCozinhaInexistente() {
		assertThrows(CozinhaNaoEncontradaException.class, () -> {
        	cadastroCozinha.excluir(100L);
	    });
    }
	
}
