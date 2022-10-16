package com.struninproject.onlinestore.repository;

import com.struninproject.onlinestore.model.Order;
import com.struninproject.onlinestore.model.User;
import com.struninproject.onlinestore.model.enums.Status;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * The {@code OrderRepository} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
public interface OrderRepository extends CrudRepository<Order,String> {
    Optional<Order> findOrderByUserAndStatus(User user, Status status);

    Optional<Order> findOrderByUserAndStatusAndProductOrdersNotNull(User user, Status status);
}
