package com.struninproject.onlinestore.controller;

import com.struninproject.onlinestore.model.Product;
import com.struninproject.onlinestore.model.User;
import com.struninproject.onlinestore.service.CategoryService;
import com.struninproject.onlinestore.service.ManufacturerService;
import com.struninproject.onlinestore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ManufacturerService manufacturerService;

    @Autowired
    public ProductController(ProductService productService,
                             CategoryService categoryService,
                             ManufacturerService manufacturerService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.manufacturerService = manufacturerService;
    }

    @GetMapping("/new")
    public ModelAndView showsRegistrationForm(ModelAndView modelAndView) {
        modelAndView.addObject("product", new Product());
        modelAndView.addObject("manufacturers", manufacturerService.findAll());
        modelAndView.addObject("categories", categoryService.findAll());
        modelAndView.setViewName("product/new");
        return modelAndView;
    }

    @PostMapping("/new")
    public ModelAndView addUser(Product product,
                                ModelAndView modelAndView) {
        productService.save(product);
        modelAndView.setViewName("redirect:/product/all");
        return modelAndView;
    }

    @GetMapping("/all")
    public ModelAndView getAllProduct(ModelAndView modelAndView) {
        modelAndView.addObject("products", productService.findAll());
        modelAndView.setViewName("product/all");
        return modelAndView;
    }

    @GetMapping("/products")
    public ModelAndView getPageProducts(
            @RequestParam(required = false, defaultValue = "name-asc") String sort,
            @RequestParam(required = false, value ="filter", defaultValue = ",") String[] filters,
            @RequestParam(required = false, defaultValue = "1", name = "page") int pageNumber,
            @RequestParam(required = false, defaultValue = "6", name = "size") int pageSize,
            ModelAndView modelAndView) {
        final String[] sortValue = sort.split("-");
        final Pageable pageable = PageRequest.of(pageNumber - 1,
                pageSize,
                Sort.by(Sort.Direction.fromString(sortValue[1]), sortValue[0]));
        final Page<Product> productPage= productService.findAllPages(pageable, filters);
        modelAndView.addObject("user", new User());                                        // FIXME: 24.10.2022
        modelAndView.addObject("categories", categoryService.findAll());
        modelAndView.addObject("manufacturers", manufacturerService.findAll());
        modelAndView.addObject("sort", sort);
        modelAndView.addObject("filters", filters);
        modelAndView.addObject("productPage", productPage);
        modelAndView.setViewName("product/products");
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView getProduct(ModelAndView modelAndView,
                             @PathVariable("id") String id) {
        modelAndView.addObject("product", productService.findById(id));
        modelAndView.addObject("user", new User());// FIXME: 24.10.2022
        modelAndView.setViewName("product/product");
        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(ModelAndView modelAndView,
                             @PathVariable("id") String id) {
        modelAndView.addObject("product", productService.findById(id));
        modelAndView.addObject("manufacturers", manufacturerService.findAll());
        modelAndView.setViewName("product/edit");
        return modelAndView;
    }

    @PatchMapping("/update")
    public String update(@ModelAttribute("user") Product product) {
        productService.update(product);
        return "redirect:/product/all";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id) {
        productService.deleteById(id);
        return "redirect:/product/all";
    }
}
