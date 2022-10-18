package com.struninproject.onlinestore.repository;

import com.struninproject.onlinestore.model.Order;
import com.struninproject.onlinestore.model.User;
import com.struninproject.onlinestore.model.enums.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * The {@code OrderRepository} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Repository
public interface OrderRepository extends CrudRepository<Order, String> {

    Optional<Order> findOrderByUserAndStatus(User user, Status status);

    Optional<Order> findOrderByUserAndStatusAndProductOrdersNotNull(User user, Status status);

    @Query("""
            SELECT SUM(po.product.price*po.quantity) FROM Order o
            JOIN ProductOrder po ON po.order.id = o.id
            WHERE o = :order
            """)
    BigDecimal getOrderTotalPrice(@Param("order") Order order);
}
