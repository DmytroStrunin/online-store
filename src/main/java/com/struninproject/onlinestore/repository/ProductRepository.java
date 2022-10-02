package com.struninproject.onlinestore.repository;

import com.struninproject.onlinestore.model.product.Product;
import org.springframework.data.repository.CrudRepository;

/**
 * The {@code ProductRepository} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
public interface ProductRepository extends CrudRepository<Product,String> {
}
