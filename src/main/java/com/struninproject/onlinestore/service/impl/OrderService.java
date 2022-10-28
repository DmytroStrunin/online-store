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
import java.util.HashSet;
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
        final ProductOrder productOrder = productOrderService.create();
        final Set<ProductOrder> productOrders = new HashSet<>();
        productOrder.setOrder(order);
        productOrder.setProduct(productService.findById(productId));
        productOrder.setQuantity(1);
        productOrders.add(productOrder);
        order.setUser(user);
        order.setStatus(Status.CART);
        order.setProductOrders(productOrders);
        log.info("Saving new Order with User email: {} status: {} product: {} "
                , user.getEmail()
                , order.getStatus(),
                productOrder.getProduct());
        return repository.save(order);
    }

    public Set<ProductDTO> updateUserCartStatus(User user) {
        repository
                .findOrderByUserAndStatusAndProductOrdersNotNull(user, Status.CART)
                .ifPresent((o) -> {
                    o.setCreated(LocalDateTime.now());
                    o.setStatus(Status.IN_PROGRESS);
                    o.setTotalPrice(repository.getOrderTotalPrice(o));
                    log.info("Saving new Order with User email: {} status: {}"
                            , user.getEmail()
                            , o.getStatus());
                    repository.save(o);
                });
        return getUserCart(user);
    }

    public Set<ProductDTO> addProductInCart(User user, String productId) {
        repository.findOrderByUserAndStatus(user, Status.CART)
                .ifPresentOrElse(
                        o -> o.getProductOrders().stream()
                                .filter(po -> po.getProduct().getId().equals(productId))
                                .findAny()
                                .ifPresentOrElse(po -> {
                                    int quantity = po.getQuantity();
                                    po.setQuantity(++quantity);
                                    repository.save(o);
                                },() -> {
                                    final ProductOrder productOrder = productOrderService.create();
                                    productOrder.setOrder(o);
                                    productOrder.setProduct(productService.findById(productId));
                                    productOrder.setQuantity(1);
                                    productOrderService.save(productOrder);
                                    o.getProductOrders().add(productOrder);
                                    repository.save(o);
                                })
                        ,
                        () -> createAndSaveWithStatusCart(user, productId));
        return getUserCart(user);
    }

    public Set<ProductDTO> removeProductFromCart(User user, String productId) {
        repository.findOrderWithStatusCard(user, productId).ifPresent(
                (o) -> {
                    o.getProductOrders().stream()
                            .filter(po -> po.getProduct().getId().equals(productId))
                            .findAny()
                            .ifPresent(po -> removeFromOrder(o, po));
                    repository.save(o);
                });
        return getUserCart(user);
    }

    public void removeFromOrder(Order order, ProductOrder productOrder) {
        int quantity = productOrder.getQuantity();
        if (quantity > 1) {
            productOrder.setQuantity(--quantity);
            order.getProductOrders().add(productOrder);
        } else {
            order.getProductOrders().remove(productOrder);
        }
    }

    public Set<ProductDTO> getUserCart(User user) {
        return productService.findAllProductsInUserCart(user);
    }
}
