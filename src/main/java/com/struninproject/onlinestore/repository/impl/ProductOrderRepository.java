package com.struninproject.onlinestore.repository.impl;

import com.struninproject.onlinestore.model.entity.Order;
import com.struninproject.onlinestore.model.entity.Product;
import com.struninproject.onlinestore.model.entity.ProductOrder;
import com.struninproject.onlinestore.repository.CommonRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The {@code ProductOrderRepository} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Repository
public interface ProductOrderRepository extends CommonRepository<ProductOrder> {

    Optional<ProductOrder> findProductOrderByOrderAndProduct(Order order, Product product);
}
