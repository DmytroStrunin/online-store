package com.struninproject.onlinestore.repository.impl;

import com.struninproject.onlinestore.model.entity.Order;
import com.struninproject.onlinestore.model.entity.User;
import com.struninproject.onlinestore.model.enums.Status;
import com.struninproject.onlinestore.repository.CommonRepository;
import org.springframework.data.jpa.repository.Query;
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
public interface OrderRepository extends CommonRepository<Order> {

    Optional<Order> findOrderByUserAndStatus(User user, Status status);

    Optional<Order> findOrderByUserAndStatusAndProductOrdersNotNull(User user, Status status);

    @Query("""
            SELECT SUM(po.product.price*po.quantity) FROM Order o
            JOIN ProductOrder po ON po.order.id = o.id
            WHERE o = :order
            """)
    BigDecimal getOrderTotalPrice(@Param("order") Order order);

    @Query("""
            SELECT o FROM Order o
            JOIN ProductOrder po ON po.order.id = o.id
            JOIN Product p ON p.id = po.product.id
            JOIN User u ON u.id = o.user.id
            WHERE o.status = 'CART'
            AND u = :user
            AND po.product.id = :productId
            """)
    Optional<Order> findOrderWithStatusCart(@Param("user") User user, @Param("productId") String productId);
}
