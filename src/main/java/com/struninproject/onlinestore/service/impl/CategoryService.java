package com.struninproject.onlinestore.service.impl;

import com.struninproject.onlinestore.model.Category;
import com.struninproject.onlinestore.repository.impl.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The {@code CategoryService} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Service
public class CategoryService extends AbstractService<Category, CategoryRepository> {

    @Autowired
    public CategoryService(CategoryRepository repository) {
        super(repository);
    }

    @Override
    public Category create() {
        return new Category();
    }

    @Override
    public Category save(Category category) {
        category.getFeatures().removeIf(String::isBlank);
        return super.save(category);
    }

    @Override
    public void update(Category category) {
        category.getFeatures().removeIf(String::isBlank);
        super.update(category);
    }
}
