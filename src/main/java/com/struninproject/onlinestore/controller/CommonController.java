package com.struninproject.onlinestore.controller;

import com.struninproject.onlinestore.model.AbstractEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * The {@code CommonController} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
public interface CommonController<E extends AbstractEntity> {

    @PostMapping
    ResponseEntity<E> save(@RequestBody E entity);

//остальные методы
}
