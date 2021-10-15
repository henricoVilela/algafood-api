package com.study.algafood;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.annotation.security.RunAs;
import javax.validation.ConstraintViolationException;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.study.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.study.algafood.domain.exception.EntidadeEmUsoException;
import com.study.algafood.domain.model.Cozinha;
import com.study.algafood.domain.repository.CozinhaRepository;
import com.study.algafood.domain.service.CadastroCozinhaService;
import com.study.algafood.util.DatabaseCleaner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunAs("com.study")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@LocalServerPort
	private int port = 8080;
	
	@BeforeEach
	public void setup() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.basePath = "/cozinhas";
		RestAssured.port = port;
		
		databaseCleaner.clearTables();
		preparaDados();
	}
	
	private void preparaDados() {
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Tailandesa");
		cozinhaRepository.save(cozinha1);
		
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Americana");
		cozinhaRepository.save(cozinha2);
	}
	
	/*
	 * Api Tests
	 */

	
	@Test
	public void deveRetornarStatus200_ConsultarCozinhas() {
		
		RestAssured.given()
				   .accept(ContentType.JSON)
				 .when()
				   .get()
				 .then()
				   .statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornar201_CadastroCozinha() {
		RestAssured.given()
				   .body("{\"nome\": \"Cozinha jUnit\"}")
				   .contentType(ContentType.JSON)
				   .accept(ContentType.JSON)
				 .when()
				   .post()
				 .then()
				   .statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
		RestAssured.given()
			.pathParam("cozinhaId", 2)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", Matchers.equalTo("Americana"));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
		RestAssured.given()
			.pathParam("cozinhaId", 100)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	/*
	 * Integration tests
	 */
	
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
			
		assertThrows(ConstraintViolationException.class, () -> {
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
