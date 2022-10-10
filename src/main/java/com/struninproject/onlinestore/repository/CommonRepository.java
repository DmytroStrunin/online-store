package com.struninproject.onlinestore.repository;

import com.struninproject.onlinestore.model.AbstractEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * The {@code CommonRepository} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@NoRepositoryBean
public interface CommonRepository<E extends AbstractEntity> extends CrudRepository<E, String> {
}
