package com.struninproject.onlinestore.controller;

import com.struninproject.onlinestore.model.Category;
import com.struninproject.onlinestore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * The {@code CategoryController} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Controller
@RequestMapping(path = "/category")
public class CategoryController {
    private final CategoryService categoryService;


    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/new")
    public ModelAndView showsNewItemForm(ModelAndView modelAndView) {
        modelAndView.addObject("category", new Category());
        modelAndView.setViewName("category/new");
        return modelAndView;
    }

    @PostMapping("/new")
    public ModelAndView addNewItem(Category category, ModelAndView modelAndView) {
        categoryService.save(category);
        modelAndView.setViewName("redirect:/category");
        return modelAndView;
    }


    @GetMapping
    public ModelAndView getAllItems(ModelAndView modelAndView) {
        modelAndView.addObject("categories", categoryService.findAll());
        modelAndView.setViewName("category/all");
        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(ModelAndView modelAndView, @PathVariable("id") String id) {
        modelAndView.addObject("category", categoryService.findById(id));
        modelAndView.setViewName("category/edit");
        return modelAndView;
    }

    @PatchMapping("/update")
    public String update(@ModelAttribute("category") Category category) {
        categoryService.update(category);
        return "redirect:/category";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id) {
        categoryService.deleteById(id);
        return "redirect:/category";
    }
}
