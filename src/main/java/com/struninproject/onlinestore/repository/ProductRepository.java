package com.struninproject.onlinestore.repository;

import com.struninproject.onlinestore.dto.ProductDTO;
import com.struninproject.onlinestore.model.Category;
import com.struninproject.onlinestore.model.Product;
import com.struninproject.onlinestore.model.User;
import com.struninproject.onlinestore.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;

/**
 * The {@code ProductRepository} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Repository
public interface ProductRepository extends CrudRepository<Product,String> {
    Page<Product> findAll(Pageable pageable);
    Page<Product> findAllByCategory(Category category, Pageable pageable);

    @Query("""
            SELECT new com.struninproject.onlinestore.dto.ProductDTO(p.id, p.name, p.price, p.image, po.quantity) FROM Order o
            JOIN User u ON u.id = o.user.id
            JOIN ProductOrder po ON po.order.id = o.id
            JOIN Product p ON p.id = po.product.id
            WHERE u = :user
            AND o.status = :status
            GROUP BY p.name, p.id, po.quantity
            ORDER BY p.name
            """)
    LinkedHashSet<ProductDTO> findAllProductsInUserCart(@Param("user") User user, @Param("status") Status status);

//    List<Product> findAll();
}
