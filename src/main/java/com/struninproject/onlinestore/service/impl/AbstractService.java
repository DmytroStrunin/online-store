package com.struninproject.onlinestore.service.impl;

import com.struninproject.onlinestore.model.entity.AbstractEntity;
import com.struninproject.onlinestore.repository.CommonRepository;
import com.struninproject.onlinestore.service.CommonService;
import lombok.extern.slf4j.Slf4j;

/**
 * The {@code AbstractService} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Slf4j
public abstract class AbstractService<E extends AbstractEntity, R extends CommonRepository<E>>
        implements CommonService<E> {

    protected final R repository;

    public AbstractService(R repository) {
        this.repository = repository;
    }

    @Override
    public E save(E entity){
        return repository.save(entity);
    }

    @Override
    public Iterable<E> findAll() {
        return repository.findAll();
    }

    @Override
    public E findById(String id) {
        return repository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void update(E entity) {
        if (repository.existsById(entity.getId())) {
            repository.save(entity);
        }
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}