package com.struninproject.onlinestore.service.impl;

import com.struninproject.onlinestore.model.Order;
import com.struninproject.onlinestore.model.Product;
import com.struninproject.onlinestore.model.ProductOrder;
import com.struninproject.onlinestore.repository.impl.ProductOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The {@code ProductOrderService} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
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

    public ProductOrder createAndSave(){
        return repository.save(create());
    }

    public ProductOrder getProductOrderOrCreateIfNotExist(Product product, Order order){
        final ProductOrder productOrder = repository
                .findProductOrderByOrderAndProduct(order, product)
                .orElseGet(this::createAndSave);
        productOrder.setOrder(order);
        productOrder.setProduct(product);
        repository.save(productOrder);
        return productOrder;
    }
}