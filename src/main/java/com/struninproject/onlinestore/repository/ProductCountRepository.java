package com.struninproject.onlinestore.repository;

import com.struninproject.onlinestore.model.ProductOrder;
import org.springframework.data.repository.CrudRepository;

/**
 * The {@code ProductCountRepository} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
public interface ProductCountRepository extends CrudRepository<ProductOrder,String> {
}
