package com.struninproject.onlinestore.repository;

import com.struninproject.onlinestore.model.Order;
import com.struninproject.onlinestore.model.Product;
import com.struninproject.onlinestore.model.ProductOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * The {@code ProductOrderRepository} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
public interface ProductOrderRepository extends CrudRepository<ProductOrder,String> {
    Optional<ProductOrder> findProductOrderByOrderAndProduct(Order order, Product product);

//    @Query("SELECT ProductOrder AS p FROM product_order" +
//            "LEFT JOIN Order o ON p.id = o " +
//            "GROUP BY p ORDER BY count ")
//    Optional<ProductOrder> bla();

}
