package com.struninproject.onlinestore.service.impl;

import com.struninproject.onlinestore.dto.ProductDTO;
import com.struninproject.onlinestore.model.entity.Order;
import com.struninproject.onlinestore.model.entity.Product;
import com.struninproject.onlinestore.model.entity.ProductOrder;
import com.struninproject.onlinestore.model.entity.User;
import com.struninproject.onlinestore.model.enums.Status;
import com.struninproject.onlinestore.repository.impl.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderServiceTest {
    private OrderRepository orderRepository;
    private ProductService productService;
    private ProductOrderService productOrderService;
    private OrderService target;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        productService = mock(ProductService.class);
        productOrderService = mock(ProductOrderService.class);
        target = new OrderService(
                orderRepository,
                productService,
                productOrderService);
    }

    @AfterEach
    void tearDown() {
        orderRepository = null;
        productService = null;
        productOrderService = null;
        target = null;
    }

    @Test
    void createAndSaveWithStatusCart_mustReturnCorrectOrder() {
        final User user = new User();
        final Product product = new Product();

        when(productService.findById("productId")).thenReturn(product);
        when(productOrderService.create()).thenCallRealMethod();
        when(productOrderService.create(any(Order.class), eq(product))).thenCallRealMethod();
        when(orderRepository.save(any(Order.class))).then(returnsFirstArg());

        final Order targetOrder = target.createAndSaveWithStatusCart(user, "productId");
        final ProductOrder targetProductOrder = targetOrder.getProductOrders().get(0);

        assertEquals(user, targetOrder.getUser());
        assertEquals(Status.CART, targetOrder.getStatus());
        assertEquals(targetOrder, targetProductOrder.getOrder());
        assertEquals(product, targetProductOrder.getProduct());
        assertEquals(1, targetProductOrder.getQuantity());
    }

    @Test
    void createAndSaveWithStatusCart_mustThrowException() {
        when(productOrderService.create())
                .thenReturn(new ProductOrder());
        when(productService.findById(anyString()))
                .thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class,
                () -> target.createAndSaveWithStatusCart(new User(), "noneProductId"));
    }

    @Test
    void updateUserCartStatus_shouldSaveRightOrderToRepository() {
        final User user = new User();
        final Order order = new Order();
        final Product product = new Product();
        final ProductOrder productOrder = new ProductOrder();
        product.setPrice(BigDecimal.ZERO);
        productOrder.setOrder(order);
        productOrder.setProduct(product);
        productOrder.setQuantity(2);
        order.setUser(user);
        order.setProductOrders(List.of(productOrder));

        when(orderRepository.findOrderByUserAndStatusAndProductOrdersNotNull(user, Status.CART))
                .thenReturn(Optional.of(order));
        when(orderRepository.getOrderTotalPrice(any(Order.class)))
                .thenReturn(BigDecimal.valueOf(20));
        when(orderRepository.save(any(Order.class)))
                .then(returnsFirstArg());

        target.updateUserCartStatus(user);

        assertEquals(user, order.getUser());
        assertEquals(Status.IN_PROGRESS, order.getStatus());
        assertEquals(BigDecimal.valueOf(20), order.getTotalPrice());
        assertEquals(List.of(productOrder), order.getProductOrders());
    }

    @Test
    void updateUserCartStatus_mustSaveToRepositoryIfOrderExist() {
        when(orderRepository.findOrderByUserAndStatusAndProductOrdersNotNull(any(User.class), any(Status.class)))
                .thenReturn(Optional.of(new Order()));

        target.updateUserCartStatus(new User());

        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void updateUserCartStatus_mustNotSaveToRepositoryIfOrderNotExist() {
        when(orderRepository.findOrderByUserAndStatusAndProductOrdersNotNull(any(User.class), any(Status.class)))
                .thenReturn(Optional.empty());

        target.updateUserCartStatus(new User());

        verify(orderRepository, times(0)).save(any(Order.class));
    }

    @Test
    void addProductInCart() {
    }

    @Test
    void removeProductFromCart_mustDecrementQuantityFromProductOrderWithProductId() {
        final User user = new User();
        final Order order = new Order();
        final Product product = new Product();
        final ProductOrder productOrder = new ProductOrder();
        final List<ProductOrder> productOrders = new ArrayList<>();
        product.setId("productId");
        productOrder.setProduct(product);
        productOrder.setQuantity(5);
        order.setUser(user);
        productOrders.add(productOrder);
        order.setProductOrders(productOrders);
        when(orderRepository.findOrderWithStatusCart(any(User.class), anyString()))
                .thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class)))
                .then(returnsFirstArg());

        target.removeProductFromCart(new User(), "productId");

        assertEquals(1, order.getProductOrders().size());
        assertEquals(4, order.getProductOrders().get(0).getQuantity());
    }

    @Test
    void removeProductFromCart_mustRemoveProductOrderWithProductId() {
        final User user = new User();
        final Order order = new Order();
        final Product product = new Product();
        final ProductOrder productOrder = new ProductOrder();
        final List<ProductOrder> productOrders = new ArrayList<>();
        product.setId("productId");
        productOrder.setProduct(product);
        productOrder.setQuantity(1);
        order.setUser(user);
        productOrders.add(productOrder);
        order.setProductOrders(productOrders);
        when(orderRepository.findOrderWithStatusCart(any(User.class), anyString()))
                .thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class)))
                .then(returnsFirstArg());

        target.removeProductFromCart(new User(), "productId");

        assertEquals(0, order.getProductOrders().size());
    }

    @Test
    void getUserCart_mustReturnSetWithOneProductDTO() {
        final ProductDTO productDTO = new ProductDTO();
        productDTO.setId("id");
        productDTO.setName("name");
        productDTO.setPrice(BigDecimal.TEN);
        productDTO.setImage("image");
        productDTO.setQuantity(1);
        when(productService.findAllProductsInUserCart(any(User.class)))
                .thenReturn(Set.of(productDTO));
        final Set<ProductDTO> targetSet = target.getUserCart(new User());
        final ProductDTO targetProductDTO =
                targetSet.stream()
                        .findFirst()
                        .orElseThrow(AssertionError::new);
        assertEquals(1, targetSet.size());
        assertEquals(productDTO.getId(), targetProductDTO.getId());
        assertEquals(productDTO.getName(), targetProductDTO.getName());
        assertEquals(productDTO.getPrice(), targetProductDTO.getPrice());
        assertEquals(productDTO.getImage(), targetProductDTO.getImage());
        assertEquals(productDTO.getQuantity(), targetProductDTO.getQuantity());
    }

    @Test
    void getUserCart_mustReturnEmptySet() {
        when(productService.findAllProductsInUserCart(any(User.class)))
                .thenReturn(Collections.emptySet());
        assertEquals(Collections.emptySet(), target.getUserCart(new User()));
    }
}