package com.struninproject.onlinestore.service.impl;

import com.struninproject.onlinestore.model.entity.Order;
import com.struninproject.onlinestore.model.entity.Product;
import com.struninproject.onlinestore.model.entity.ProductOrder;
import com.struninproject.onlinestore.repository.impl.ProductOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The {@code ProductOrderService} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Slf4j
@Service
public class ProductOrderService extends AbstractService<ProductOrder, ProductOrderRepository> {

    @Autowired
    public ProductOrderService(ProductOrderRepository repository) {
        super(repository);
    }

    @Override
    public ProductOrder create() {
        return new ProductOrder();
    }

    public ProductOrder create(Order order, Product product) {
        final ProductOrder productOrder = create();
        productOrder.setOrder(order);
        productOrder.setProduct(product);
        productOrder.setQuantity(1);
        return productOrder;
    }
}