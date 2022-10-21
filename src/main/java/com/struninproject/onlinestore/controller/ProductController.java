package com.struninproject.onlinestore.controller;

import com.struninproject.onlinestore.model.Product;
import com.struninproject.onlinestore.repository.CategoryRepository;
import com.struninproject.onlinestore.repository.ManufacturerRepository;
import com.struninproject.onlinestore.repository.ProductRepository;
import com.struninproject.onlinestore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * The {@code ProductController} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Controller
@RequestMapping(path = "/product")
public class ProductController {
    private final ProductRepository productRepository;

    private final ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    ManufacturerRepository manufacturerRepository;

    @Autowired
    public ProductController(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @GetMapping("/new")
    public ModelAndView showsRegistrationForm(ModelAndView modelAndView) {
        modelAndView.addObject("product", new Product());
        modelAndView.addObject("manufacturers", manufacturerRepository.findAll());  // FIXME: 11.10.2022 
        modelAndView.addObject("categories", categoryRepository.findAll());

        modelAndView.setViewName("product/new");
        return modelAndView;
    }

    @PostMapping("/new")
    public ModelAndView addUser(Product product, ModelAndView modelAndView) {
        productRepository.save(product);
        modelAndView.setViewName("redirect:/product");
        return modelAndView;
    }

    @GetMapping
    public ModelAndView getAllProduct(ModelAndView modelAndView) {
        modelAndView.addObject("products", productRepository.findAll());
        modelAndView.setViewName("product/all");
        return modelAndView;
    }

    @GetMapping("/p")
    public ModelAndView getAllProducts(ModelAndView modelAndView) {
        modelAndView.addObject("products", productRepository.findAll());
        modelAndView.setViewName("product/products");
        return modelAndView;
    }

    @GetMapping("/p1")
    public ModelAndView getAllProducts1(
            ModelAndView modelAndView,
            @RequestParam(required = false, defaultValue = "")
            String filter,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC)
            Pageable pageable,
            @RequestParam(required = false, defaultValue = "1", name = "page") int pageNumber,
            @RequestParam(required = false, defaultValue = "3", name = "size") int pageSize
    ) {
        Page<Product> productPage;

        if (filter != null && !filter.isEmpty()) {
            pageable = PageRequest.of(pageNumber - 1,
                    pageSize,
                    Sort.by(Sort.Direction.ASC, "id"));
            productPage = productRepository.findAllByCategory(categoryRepository.findByName(filter), pageable);
        } else {
            productPage = productRepository.findAll(pageable);
        }

        modelAndView.addObject("filter", filter);
        modelAndView.addObject("productPage", productPage);
        modelAndView.setViewName("product/p1");
        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(ModelAndView modelAndView, @PathVariable("id") String id) {
        modelAndView.addObject("product", productRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new));
        modelAndView.addObject("manufacturers", manufacturerRepository.findAll());// FIXME: 03.10.2022
        modelAndView.setViewName("product/edit");
        return modelAndView;
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") Product product, @PathVariable("id") String id) {
        productRepository.findById(id);
        if (productRepository.existsById(id)) {
            productRepository.save(product);
        }
        return "redirect:/product";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id) {
        productRepository.deleteById(id);
        return "redirect:/product";
    }
}
