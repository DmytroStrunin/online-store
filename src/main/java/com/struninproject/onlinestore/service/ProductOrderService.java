package com.struninproject.onlinestore.service;

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
    private final ProductOrderRepository repository;

    @Autowired
    public ProductOrderService(ProductOrderRepository repository) {
        this.repository=repository;
    }

    public ProductOrder createAndSave(){
        return repository.save(new ProductOrder());
    }
}