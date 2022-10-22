package com.struninproject.onlinestore.service;

import com.struninproject.onlinestore.model.Product;
import com.struninproject.onlinestore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * The {@code ProductService} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    public Page<Product> findAllPages(Pageable pageable, String filter) {
        if (filter != null && !filter.isEmpty()) {
            return productRepository.findAllByCategory(categoryService.findByName(filter), pageable);
        } else {
            return productRepository.findAll(pageable);
        }
    }

    public Product findById(String id) {
        return productRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    public void update(Product product) {
        if (productRepository.existsById(product.getId())) {
            productRepository.save(product);
        }
    }

    public void deleteById(String id) {
        productRepository.deleteById(id);
    }



}
