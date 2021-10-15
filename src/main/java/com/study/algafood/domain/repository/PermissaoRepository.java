package com.study.algafood.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.study.algafood.domain.model.Permissao;

@Repository
public interface PermissaoRepository  extends JpaRepository<Permissao, Long>{
	
}


/*
public interface PermissaoRepository {

    List<Permissao> listar();
    Permissao buscar(Long id);
    Permissao salvar(Permissao permissao);
    void remover(Permissao permissao);
    
}*/