package com.struninproject.onlinestore.controller.impl;

import com.struninproject.onlinestore.model.entity.Category;
import com.struninproject.onlinestore.service.impl.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The {@code CategoryController} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Controller
@RequestMapping(path = "/category")
public class CategoryController
        extends AbstractController<Category, CategoryService> {

    @Autowired
    public CategoryController(CategoryService service) {
        super(service);
    }
}
