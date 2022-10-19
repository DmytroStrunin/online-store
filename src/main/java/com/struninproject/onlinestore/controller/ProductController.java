package com.struninproject.onlinestore.controller;

import com.struninproject.onlinestore.model.Product;
import com.struninproject.onlinestore.repository.CategoryRepository;
import com.struninproject.onlinestore.repository.ManufacturerRepository;
import com.struninproject.onlinestore.repository.ProductRepository;
import com.struninproject.onlinestore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    private final ProductService productService;

    @Autowired
    ManufacturerRepository manufacturerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    public ProductController(ProductRepository repository, ProductService productService) {
        this.repository = repository;
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
        repository.save(product);
        modelAndView.setViewName("redirect:/product");
        return modelAndView;
    }

    @GetMapping
    public ModelAndView getAllProduct(ModelAndView modelAndView) {
        modelAndView.addObject("products", repository.findAll());
        modelAndView.setViewName("product/all");
        return modelAndView;
    }

    @GetMapping("/p")
    public ModelAndView getAllProducts(ModelAndView modelAndView) {
        modelAndView.addObject("products", repository.findAll());
        modelAndView.setViewName("product/products");
        return modelAndView;
    }

    @GetMapping("/p1")
    public ModelAndView getAllProducts1(
            ModelAndView modelAndView,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(3);
        Page<Product> productPage = productService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        modelAndView.addObject("productPage", productPage);

        int totalPages = productPage.getTotalPages();

        if (totalPages > 0) {

            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());

            modelAndView.addObject("pageNumbers", pageNumbers);

        }

        modelAndView.setViewName("product/p1");
        return modelAndView;
    }

//    @GetMapping("/p2")
//    public ModelAndView getAllProducts2(
//            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)
//                    Pageable pageable,
//            ModelAndView modelAndView,
//            @RequestParam("page") Optional<Integer> page,
//            @RequestParam("size") Optional<Integer> size) {
//        int currentPage = page.orElse(1);
//        int pageSize = size.orElse(5);
//        Page<Product> bookPage = productService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
//
//        modelAndView.addObject("bookPage", bookPage);
//
//        int totalPages = bookPage.getTotalPages();
//        if (totalPages > 0) {
//            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
//                    .boxed()
//                    .collect(Collectors.toList());
//            modelAndView.addObject("pageNumbers", pageNumbers);
//        }
////        Page<Product> productPage = productService.findAll(pageable);
//        modelAndView.setViewName("product/p1");
//        return modelAndView;
//    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(ModelAndView modelAndView, @PathVariable("id") String id) {
        modelAndView.addObject("product", repository.findById(id)
                .orElseThrow(IllegalArgumentException::new));
        modelAndView.addObject("manufacturers", manufacturerRepository.findAll());// FIXME: 03.10.2022
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
