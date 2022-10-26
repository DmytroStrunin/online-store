package com.struninproject.onlinestore.service;

import com.struninproject.onlinestore.model.Category;
import com.struninproject.onlinestore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The {@code CategoryService} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void save(Category category) {
        category.getFeatures().removeIf(String::isBlank);
        categoryRepository.save(category);
    }

    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    public void update(Category category) {
        category.getFeatures().removeIf(String::isBlank);
        if (categoryRepository.existsById(category.getId())) {
            categoryRepository.save(category);
        }
    }

    public void deleteById(String id) {
        categoryRepository.deleteById(id);
    }

    public Category findByName(String filter) {
       return categoryRepository.findByName(filter);
    }
}
