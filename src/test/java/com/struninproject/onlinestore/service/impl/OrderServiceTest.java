package com.struninproject.onlinestore.service.impl;

import com.struninproject.onlinestore.dto.ProductDTO;
import com.struninproject.onlinestore.model.entity.Order;
import com.struninproject.onlinestore.model.entity.Product;
import com.struninproject.onlinestore.model.entity.ProductOrder;
import com.struninproject.onlinestore.model.entity.User;
import com.struninproject.onlinestore.model.enums.Status;
import com.struninproject.onlinestore.repository.impl.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class OrderServiceTest {
    private OrderRepository orderRepository;
    private ProductService productService;
    private ProductOrderService productOrderService;
    private OrderService target;

    @BeforeEach
    void setUp() {
        orderRepository = Mockito.mock(OrderRepository.class);
        productService = Mockito.mock(ProductService.class);
        productOrderService = Mockito.mock(ProductOrderService.class);
        target = new OrderService(
                orderRepository,
                productService,
                productOrderService);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createAndSaveWithStatusCartMustBeReturnCorrectOrder() {
        final Product product = new Product();
        final ProductOrder productOrder = new ProductOrder();
        final User user = new User();
        Mockito.when(productService.findById("productId"))
                .thenReturn(product);
        Mockito.when(productOrderService.create())
                .thenReturn(productOrder);
        Mockito.when(orderRepository.save(any(Order.class)))
                .then(returnsFirstArg());
        final Order targetOrder = target.createAndSaveWithStatusCart(user, "productId");
        final ProductOrder targetProductOrder = targetOrder.getProductOrders().get(0);
        Assertions.assertEquals(user, targetOrder.getUser());
        Assertions.assertEquals(Status.CART, targetOrder.getStatus());
        Assertions.assertEquals(targetOrder, targetProductOrder.getOrder());
        Assertions.assertEquals(product, targetProductOrder.getProduct());
        Assertions.assertEquals(1, targetProductOrder.getQuantity());
    }

    @Test
    void createAndSaveWithStatusCartMustThrowException() {
        Mockito.when(productOrderService.create())
                .thenReturn(new ProductOrder());
        Mockito.when(productService.findById(anyString()))
                .thenThrow(IllegalArgumentException.class);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> target.createAndSaveWithStatusCart(new User(), "noneProductId"));
    }

    @Test
    void updateUserCartStatusMustReturnOneProductDTOinSet() {
        final Order order = new Order();
        final Product product = new Product();
        final ProductOrder productOrder = new ProductOrder();
        final User user = new User();
        final ProductDTO productDTO = new ProductDTO(
                null,
                null,
                BigDecimal.TEN,
                null,
                2);
        product.setPrice(BigDecimal.ZERO);
        productOrder.setOrder(order);
        productOrder.setProduct(product);
        productOrder.setQuantity(2);
        order.setUser(user);
        order.setProductOrders(List.of(productOrder));
        Mockito.when(orderRepository.findOrderByUserAndStatusAndProductOrdersNotNull(user, Status.CART))
                .thenReturn(Optional.of(order));
        Mockito.when(orderRepository.getOrderTotalPrice(any(Order.class)))
                .thenReturn(BigDecimal.valueOf(20));
        Mockito.when(orderRepository.save(any(Order.class)))
                .then(returnsFirstArg());
        Mockito.when(productService.findAllProductsInUserCart(user))
                .thenReturn(Set.of(productDTO));
        final ProductDTO targetProductDTO = target.updateUserCartStatus(user).stream()
                .findFirst()
                .get();
        Assertions.assertEquals(order, orderRepository.save(order));
        Assertions.assertEquals(order.getTotalPrice(), BigDecimal.valueOf(20));
        Assertions.assertEquals(order.getStatus(), Status.IN_PROGRESS);
        Assertions.assertEquals(productDTO.getPrice(), targetProductDTO.getPrice());
        Assertions.assertEquals(productDTO.getQuantity(), targetProductDTO.getQuantity());
    }

    @Test
    void updateUserCartStatusMustReturnEmptySet() {
        final User user = new User();
        Mockito.when(orderRepository.findOrderByUserAndStatusAndProductOrdersNotNull(user, Status.CART))
                .thenReturn(Optional.empty());
        Assertions.assertEquals(Set.of(), target.updateUserCartStatus(user));
    }

    @Test
    void addProductInCart() {
    }

    @Test
    void removeProductFromCart() {
    }

    @Test
    void removeFromOrder() {
    }

    @Test
    void getUserCart() {
    }
}