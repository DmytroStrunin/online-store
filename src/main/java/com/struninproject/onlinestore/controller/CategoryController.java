package com.struninproject.onlinestore.controller;

import com.struninproject.onlinestore.model.Category;
import com.struninproject.onlinestore.repository.CategoryRepository;
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

import javax.annotation.PostConstruct;

/**
 * The {@code CategoryController} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Controller
@RequestMapping(path = "/category")
public class CategoryController {

    private final CategoryRepository repository;


    @PostConstruct
    public void addProducts() {
    }

    @Autowired
    public CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/new")
    public ModelAndView showsNewItemForm(ModelAndView modelAndView) {
        modelAndView.addObject("category", new Category());
        modelAndView.setViewName("category/new");
        return modelAndView;
    }

    @PostMapping("/new")
    public ModelAndView addNewItem(Category category, ModelAndView modelAndView) {
        category.getFeatures().remove("");
        repository.save(category);
        modelAndView.setViewName("redirect:/category");
        return modelAndView;
    }

//    @PostMapping("/new1")
//    public ModelAndView addNewItem1(Category category, ModelAndView modelAndView) {
//        modelAndView.setViewName("redirect:/category/new");
//        return modelAndView;
//    }

    @GetMapping
    public ModelAndView getAllItems(ModelAndView modelAndView) {
        modelAndView.addObject("categories", repository.findAll());
        modelAndView.setViewName("category/all");
        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(ModelAndView modelAndView, @PathVariable("id") String id) {
        modelAndView.addObject("category", repository.findById(id).get());// FIXME: 03.10.2022
        modelAndView.setViewName("category/edit");
        return modelAndView;
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("category") Category category, @PathVariable("id") String id) {
        category.getFeatures().remove("");
        repository.findById(id);
        if (repository.existsById(id)) {
            repository.save(category);
        }
        return "redirect:/category";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id) {
        repository.deleteById(id);
        return "redirect:/category";
    }
}
