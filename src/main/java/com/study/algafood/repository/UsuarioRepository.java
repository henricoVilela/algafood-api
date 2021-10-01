package com.study.algafood.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.study.algafood.model.Usuario;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {
	
	Optional<Usuario> findByEmail(String email);
}