package com.struninproject.onlinestore.service;

import com.struninproject.onlinestore.model.Order;
import com.struninproject.onlinestore.model.Product;
import com.struninproject.onlinestore.model.ProductOrder;
import com.struninproject.onlinestore.repository.ProductOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The {@code ProductOrderService} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Service
public class ProductOrderService {
    private final ProductOrderRepository productOrderRepository;

    @Autowired
    public ProductOrderService(ProductOrderRepository productOrderRepository) {
        this.productOrderRepository=productOrderRepository;
    }

    public ProductOrder createAndSave(){
        return productOrderRepository.save(new ProductOrder());
    }

    public ProductOrder getProductOrderOrCreateIfNotExist(Product product, Order order){
        final ProductOrder productOrder = productOrderRepository
                .findProductOrderByOrderAndProduct(order, product)
                .orElseGet(this::createAndSave);
        productOrder.setOrder(order);
        productOrder.setProduct(product);
        productOrderRepository.save(productOrder);
        return productOrder;
    }
}