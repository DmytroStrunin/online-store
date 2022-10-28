package com.struninproject.onlinestore.controller.impl;

import com.struninproject.onlinestore.model.Product;
import com.struninproject.onlinestore.model.User;
import com.struninproject.onlinestore.service.impl.CategoryService;
import com.struninproject.onlinestore.service.impl.ManufacturerService;
import com.struninproject.onlinestore.service.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
public class ProductController extends AbstractController<Product, ProductService> {
    private final CategoryService categoryService;
    private final ManufacturerService manufacturerService;

    @Autowired
    public ProductController(ProductService service,
                             CategoryService categoryService,
                             ManufacturerService manufacturerService) {
        super(service);
        this.categoryService = categoryService;
        this.manufacturerService = manufacturerService;
    }

    @GetMapping("/new")
    @Override
    public ModelAndView showsNewForm(ModelAndView modelAndView) {
        modelAndView.addObject("data", new Product());
        modelAndView.addObject("manufacturers", manufacturerService.findAll());
        modelAndView.addObject("categories", categoryService.findAll());
        modelAndView.setViewName("product/new");
        return modelAndView;
    }

    @GetMapping("/products")
    public ModelAndView getPageProducts(
            @RequestParam(required = false, defaultValue = "name-asc") String sort,
            @RequestParam(required = false, value = "filter", defaultValue = ",") String[] filters,
            @RequestParam(required = false, defaultValue = "1", name = "page") int pageNumber,
            @RequestParam(required = false, defaultValue = "6", name = "size") int pageSize,
            ModelAndView modelAndView) {
        final Page<Product> productPage =
                service.findAllPages(sort, filters, pageNumber, pageSize);
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
        modelAndView.addObject("user", new User());                                       // FIXME: 24.10.2022
        modelAndView.addObject("product", service.findById(id));
        modelAndView.setViewName("product/product");
        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    @Override
    public ModelAndView edit(ModelAndView modelAndView,@PathVariable("id") String id) {
        modelAndView.addObject("product", service.findById(id));
        modelAndView.addObject("manufacturers", manufacturerService.findAll());
        modelAndView.setViewName("product/edit");
        return modelAndView;
    }
}
