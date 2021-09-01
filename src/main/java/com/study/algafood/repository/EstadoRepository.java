package com.study.algafood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.study.algafood.model.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long>{
	
}

/*
public interface EstadoRepository {

    List<Estado> listar();
    Estado buscar(Long id);
    Estado salvar(Estado estado);
    void remover(Long id);
    
}*/