package com.struninproject.onlinestore.service;

import com.struninproject.onlinestore.model.AbstractEntity;

import java.util.Optional;

/**
 * The {@code CommonService} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
public interface CommonService<E extends AbstractEntity> {

    Optional<E> save(E entity);
//какое-то количество нужных нам методов
}