package com.struninproject.onlinestore.service;

import com.struninproject.onlinestore.model.AbstractEntity;
import com.struninproject.onlinestore.repository.CommonRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The {@code AbstractService} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
public abstract class AbstractService<E extends AbstractEntity, R extends CommonRepository<E>>
        implements CommonService<E> {

    protected final R repository;

    @Autowired
    public AbstractService(R repository) {
        this.repository = repository;
    }

//другие методы, переопределённые из интерфейса
}