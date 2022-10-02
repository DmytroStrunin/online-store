package com.struninproject.onlinestore.repository;

import com.struninproject.onlinestore.model.Order;
import org.springframework.data.repository.CrudRepository;

/**
 * The {@code OrderRepository} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
public interface OrderRepository extends CrudRepository<Order,String> {
}
