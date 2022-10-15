package com.struninproject.onlinestore.service;

import com.struninproject.onlinestore.model.Order;
import com.struninproject.onlinestore.model.User;
import com.struninproject.onlinestore.model.enums.Status;
import com.struninproject.onlinestore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

/**
 * The {@code OrderService} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository =orderRepository;
    }

    public Order createAndSave(User user) {
        final Order order = new Order();
        order.setUser(user);
        order.setStatus(Status.CART);
        order.setProductOrders(new HashSet<>());
        return orderRepository.save(order);
    }
}
