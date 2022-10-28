package com.struninproject.onlinestore.service.impl;

import com.struninproject.onlinestore.dto.ProductDTO;
import com.struninproject.onlinestore.model.Order;
import com.struninproject.onlinestore.model.Product;
import com.struninproject.onlinestore.model.ProductOrder;
import com.struninproject.onlinestore.model.User;
import com.struninproject.onlinestore.model.enums.Status;
import com.struninproject.onlinestore.repository.impl.OrderRepository;
import com.struninproject.onlinestore.repository.impl.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@code OrderService} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Service
public class OrderService extends AbstractService<Order, OrderRepository> {
    private final ProductRepository productRepository;
    private final ProductOrderService productOrderService;

    @Autowired
    public OrderService(OrderRepository repository,
                        ProductRepository productRepository,
                        ProductOrderService productOrderService) {
        super(repository);
        this.productRepository = productRepository;
        this.productOrderService = productOrderService;
    }

    @Override
    public Order create() {
        return new Order();
    }

    public Order createAndSave(User user) {
        final Order order = create();
        order.setUser(user);
        order.setStatus(Status.CART);
        order.setProductOrders(new HashSet<>());
        return repository.save(order);
    }

    public Set<ProductDTO> updateUserCartStatus(User user) {
        repository
                .findOrderByUserAndStatusAndProductOrdersNotNull(user, Status.CART)
                .ifPresent((o) -> {
                    o.setCreated(LocalDateTime.now());
                    o.setStatus(Status.IN_PROGRESS);
                    o.setTotalPrice(repository.getOrderTotalPrice(o));
                    repository.save(o);
                });
        return getUserCart(user);
    }

    public Set<ProductDTO> addProductInCart(User user, String productId) {
        final Product product = productRepository
                .findById(productId)
                .orElseThrow(IllegalArgumentException::new);

        final Order order = repository
                .findOrderByUserAndStatus(user, Status.CART)
                .orElseGet(() -> createAndSave(user));

        final ProductOrder productOrder = productOrderService
                .getProductOrderOrCreateIfNotExist(product, order);

        int quantity = productOrder.getQuantity();
        productOrder.setQuantity(++quantity);

        repository.save(order);

        return getUserCart(user);
    }

    public Set<ProductDTO> removeProductInCart(User user, String productId) {
        final Product product = productRepository
                .findById(productId)
                .orElseThrow(IllegalArgumentException::new);  // FIXME: 15.10.2022

        final Order order = repository
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

        repository.save(order);

        return getUserCart(user);
    }

    public Set<ProductDTO> getUserCart(User user) {
        return productRepository.findAllProductsInUserCart(user, Status.CART);
    }
}
