package com.study.algafood.infrastructure.repository;
/*
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.study.algafood.model.Estado;
import com.study.algafood.repository.EstadoRepository;

@Repository
public class EstadoRepositoryImpl implements EstadoRepository {

    @PersistenceContext
    private EntityManager manager;
    
    @Override
    public List<Estado> listar() {
        return manager.createQuery("from Estado", Estado.class)
                .getResultList();
    }
    
    @Override
    public Estado buscar(Long id) {
        return manager.find(Estado.class, id);
    }
    
    @Transactional
    @Override
    public Estado salvar(Estado estado) {
        return manager.merge(estado);
    }
    
    @Transactional
    @Override
    public void remover(Long id) {
    	Estado estado = buscar(id);
        
        if (estado == null) {
            throw new EmptyResultDataAccessException(1);
        }
        
        manager.remove(estado);	
    }

}*/