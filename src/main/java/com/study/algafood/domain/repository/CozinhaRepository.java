package com.study.algafood.domain.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.study.algafood.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {

	public List<Cozinha> findByNomeContaining(String nome);
	
	public List<Cozinha> consultarPorNome(String nome);
}


/* Antes do JPA
public interface CozinhaRepository {
	public List<Cozinha> listar();
	public Cozinha buscar(Long id);
	public Cozinha salvar(Cozinha cozinha);
	public void remover(Long id);
	public List<Cozinha> listarPorNome(String nome);
}
*/
