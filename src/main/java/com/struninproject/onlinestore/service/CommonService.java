package com.struninproject.onlinestore.service;

import com.struninproject.onlinestore.model.AbstractEntity;

/**
 * The {@code CommonService} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
public interface CommonService<E extends AbstractEntity> {

    E save(E entity);

    Iterable<E> findAll();

    E findById(String id);

    void update(E entity);

    void deleteById(String id);

    E create();
}