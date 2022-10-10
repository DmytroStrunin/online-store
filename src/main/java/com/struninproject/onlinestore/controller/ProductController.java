package com.struninproject.onlinestore.controller;

import com.struninproject.onlinestore.model.Product;
import com.struninproject.onlinestore.repository.CategoryRepository;
import com.struninproject.onlinestore.repository.ManufacturerRepository;
import com.struninproject.onlinestore.repository.ProductRepository;
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
 * The {@code ProductController} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Controller
@RequestMapping(path = "/product")
public class ProductController {
    private final ProductRepository repository;

    //    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    ProductCountRepository productCountRepository;
//    @Autowired
//    ProductRepository productRepository;
    @Autowired
    ManufacturerRepository manufacturerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @PostConstruct
    public void addProducts() {
    }

    @Autowired
    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/new")
    public ModelAndView showsRegistrationForm(ModelAndView modelAndView) {
        modelAndView.addObject("product", new Product());
        modelAndView.addObject("manufacturers", manufacturerRepository.findAll());
        modelAndView.addObject("categories", categoryRepository.findAll());
        modelAndView.setViewName("product/new");
        return modelAndView;
    }

    @PostMapping("/new")
    public ModelAndView addUser(Product product, ModelAndView modelAndView) {
        repository.save(product);
        modelAndView.setViewName("redirect:/product");
        return modelAndView;
    }

    @GetMapping
    public ModelAndView getAllProducts(ModelAndView modelAndView) {
        modelAndView.addObject("products", repository.findAll());
        modelAndView.setViewName("product/all");
        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(ModelAndView modelAndView, @PathVariable("id") String id) {
        modelAndView.addObject("product", repository.findById(id).get());// FIXME: 03.10.2022
        modelAndView.setViewName("product/edit");
        return modelAndView;
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") Product product, @PathVariable("id") String id) {
        repository.findById(id);
        if (repository.existsById(id)) {
            repository.save(product);
        }
        return "redirect:/product";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id) {
        repository.deleteById(id);
        return "redirect:/product";
    }
}
