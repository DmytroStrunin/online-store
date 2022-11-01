package com.struninproject.onlinestore.service.impl;

import com.struninproject.onlinestore.dto.ProductDTO;
import com.struninproject.onlinestore.model.entity.Order;
import com.struninproject.onlinestore.model.entity.ProductOrder;
import com.struninproject.onlinestore.model.entity.User;
import com.struninproject.onlinestore.model.enums.Status;
import com.struninproject.onlinestore.repository.impl.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The {@code OrderService} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Slf4j
@Service
public class OrderService extends AbstractService<Order, OrderRepository> {
    private final ProductService productService;
    private final ProductOrderService productOrderService;

    @Autowired
    public OrderService(OrderRepository repository,
                        ProductService productService,
                        ProductOrderService productOrderService) {
        super(repository);
        this.productService = productService;
        this.productOrderService = productOrderService;
    }

    @Override
    public Order create() {
        return new Order();
    }

    public Order createAndSaveWithStatusCart(User user, String productId) {
        final Order order = create();
        final List<ProductOrder> productOrders = new ArrayList<>();
        final ProductOrder productOrder =
                productOrderService
                        .create(order, productService.findById(productId));
        productOrders.add(productOrder);
        order.setUser(user);
        order.setStatus(Status.CART);
        order.setProductOrders(productOrders);
        log.info("Saving new Order with User email: {} status: {} product: {} "
                , user.getEmail()
                , order.getStatus(),
                productOrder.getProduct().getId());
        return repository.save(order);
    }

    public Set<ProductDTO> updateUserCartStatus(User user) {
        repository
                .findOrderByUserAndStatusAndProductOrdersNotNull(user, Status.CART)
                .ifPresent(
                        order -> {
                            order.setCreated(LocalDateTime.now());
                            order.setStatus(Status.IN_PROGRESS);
                            order.setTotalPrice(repository.getOrderTotalPrice(order));
                            log.info("Saving new Order with User email: {} status: {}"
                                    , user.getEmail()
                                    , order.getStatus());
                            repository.save(order);
                        });
        return getUserCart(user);
    }

    public Set<ProductDTO> addProductInCart(User user, String productId) {
        repository.findOrderByUserAndStatus(user, Status.CART)
                .ifPresentOrElse(
                        order -> order.getProductOrders().stream()
                                .filter(po -> po.getProduct().getId().equals(productId))
                                .findAny()
                                .ifPresentOrElse(
                                        po -> {
                                            int quantity = po.getQuantity();
                                            po.setQuantity(++quantity);
                                            repository.save(order);
                                        }, () -> {
                                            final ProductOrder productOrder =
                                                    productOrderService.create(
                                                            order,
                                                            productService.findById(productId));
                                            order.getProductOrders().add(productOrder);
                                            repository.save(order);
                                        }),
                        () -> createAndSaveWithStatusCart(user, productId));
        return getUserCart(user);
    }

    public Set<ProductDTO> removeProductFromCart(User user, String productId) {
        repository.findOrderWithStatusCart(user, productId)
                .ifPresent(
                        order -> {
                            order.getProductOrders().stream()
                                    .filter(po -> po.getProduct().getId().equals(productId))
                                    .findAny()
                                    .ifPresent(po -> removeFromOrder(order, po));
                            repository.save(order);
                        });
        return getUserCart(user);
    }

    private void removeFromOrder(Order order, ProductOrder productOrder) {
        int quantity = productOrder.getQuantity();
        if (quantity > 1) {
            productOrder.setQuantity(--quantity);
        } else {
            order.getProductOrders().remove(productOrder);
        }
    }

    public Set<ProductDTO> getUserCart(User user) {
        return productService.findAllProductsInUserCart(user);
    }
}
