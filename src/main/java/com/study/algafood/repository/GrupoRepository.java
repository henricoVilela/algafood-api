package com.study.algafood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.study.algafood.model.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {

}