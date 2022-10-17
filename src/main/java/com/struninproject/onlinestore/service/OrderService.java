package com.struninproject.onlinestore.service;

import com.struninproject.onlinestore.dto.OrderDTO;
import com.struninproject.onlinestore.dto.ProductDTO;
import com.struninproject.onlinestore.model.Order;
import com.struninproject.onlinestore.model.Product;
import com.struninproject.onlinestore.model.ProductOrder;
import com.struninproject.onlinestore.model.User;
import com.struninproject.onlinestore.model.enums.Status;
import com.struninproject.onlinestore.repository.OrderRepository;
import com.struninproject.onlinestore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * The {@code OrderService} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProductOrderService productOrderService;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        ProductOrderService productOrderService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.productOrderService = productOrderService;
    }

    public Order createAndSave(User user) {
        final Order order = new Order();
        order.setUser(user);
        order.setStatus(Status.CART);
        order.setProductOrders(new HashSet<>());
        return orderRepository.save(order);
    }

    public OrderDTO getUserCart(User user) {
        final Set<ProductDTO> products = productRepository.findAllProductsInUserCart(user, Status.CART);
        final BigDecimal totalPrice = products.stream()
                .map(ProductDTO::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new OrderDTO(totalPrice, products);
    }

    public void updateUserCartStatus(User user) {
        orderRepository
                .findOrderByUserAndStatusAndProductOrdersNotNull(user, Status.CART)
                .ifPresent((o) -> {
                    o.setCreated(LocalDateTime.now());
                    o.setStatus(Status.IN_PROGRESS);
                    orderRepository.save(o);
                });
    }

    public void addProductInCart(User user, String productId) {
        final Product product = productRepository
                .findById(productId)
                .orElseThrow(IllegalArgumentException::new);


        final Order order = orderRepository
                .findOrderByUserAndStatus(user, Status.CART)
                .orElseGet(() -> createAndSave(user));


        final ProductOrder productOrder = productOrderService
                .getProductOrderOrCreateIfNotExist(product, order);

        int quantity = productOrder.getQuantity();
        productOrder.setQuantity(++quantity);

        orderRepository.save(order);
    }

    public void removeProductInCart(User user, String productId) {
        final Product product = productRepository
                .findById(productId)
                .orElseThrow(IllegalArgumentException::new);  // FIXME: 15.10.2022

        final Order order = orderRepository
                .findOrderByUserAndStatus(user, Status.CART)
                .orElseGet(() -> createAndSave(user));

//        final Order order = orderRepository
//                .findOrderByUserAndStatus(user, Status.CART)
//                .orElseThrow(IllegalArgumentException::new);

//        final ProductOrder productOrder = productOrderRepository
//                .findProductOrderByOrderAndProduct(order, product)
//                .orElseThrow(IllegalArgumentException::new);
        final ProductOrder productOrder = productOrderService
                .getProductOrderOrCreateIfNotExist(product, order);

        if (productOrder.getQuantity() > 1) {
            productOrder.setQuantity(productOrder.getQuantity() - 1);
            order.getProductOrders().add(productOrder); // FIXME: 17.10.2022
        } else {
            order.getProductOrders().remove(productOrder);
        }

        orderRepository.save(order);
    }

    public Optional<Order> findById(String id){
        return orderRepository.findById(id);
    }

    public Iterable<Order> findAll(){
       return orderRepository.findAll();
    }


    public void update(Order order){
        if (orderRepository.existsById(order.getId())) {
            orderRepository.save(order);
        }
    }

    public void deleteById(String id){
        orderRepository.deleteById(id);
    }
}
