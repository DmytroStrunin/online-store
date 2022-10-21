package com.struninproject.onlinestore.service;

import com.struninproject.onlinestore.model.Product;
import com.struninproject.onlinestore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * The {@code ProductService} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> findAll(Pageable pageable) {
        final Page<Product> products = productRepository.findAll(pageable);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Product> list;

        if (products.getTotalPages() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, products.getTotalPages());
            list = products.getContent().subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), products.getTotalPages());
    }
}
