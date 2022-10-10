package com.struninproject.onlinestore.controller;

import com.struninproject.onlinestore.model.AbstractEntity;
import com.struninproject.onlinestore.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The {@code AbstractController} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
public abstract class AbstractController<E extends AbstractEntity, S extends CommonService<E>>
        implements CommonController<E> {

    private final S service;

    @Autowired
    protected AbstractController(S service) {
        this.service = service;
    }

//    @Override
//    public ResponseEntity<E> save(@RequestBody E entity) {
//        return service.save(entity).map(ResponseEntity::ok)
//                .orElseThrow(() -> new SampleException(
//                        String.format(ErrorType.ENTITY_NOT_SAVED.getDescription(), entity.toString())
//                ));
//    }

//другие методы
}