package com.struninproject.onlinestore.service.impl;

import com.struninproject.onlinestore.dto.ProductDTO;
import com.struninproject.onlinestore.model.Product;
import com.struninproject.onlinestore.model.User;
import com.struninproject.onlinestore.model.enums.Status;
import com.struninproject.onlinestore.repository.impl.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * The {@code ProductService} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Service
public class ProductService extends AbstractService<Product, ProductRepository> {

    @Autowired
    public ProductService(ProductRepository repository) {
        super(repository);
    }

    @Override
    public Product create() {
        return new Product();
    }

    public Page<Product> findAllPages(String sort, String[] filters, int pageNumber, int pageSize) {
        final String[] sortValue = sort.split("-");
        final Pageable pageable = PageRequest.of(pageNumber - 1,
                pageSize,
                Sort.by(Sort.Direction.fromString(sortValue[1]), sortValue[0]));
        final String categoryName = filters[0];
        final String manufacturerName = filters[1];
        return repository
                .findAllByCategory_NameContainsIgnoreCaseAndManufacturer_NameContainsIgnoreCase(
                        categoryName,
                        manufacturerName,
                        pageable);
    }

    public Set<ProductDTO> findAllProductsInUserCart(User user) {
        return repository.findAllProductsInUserCart(user, Status.CART);
    }
}
