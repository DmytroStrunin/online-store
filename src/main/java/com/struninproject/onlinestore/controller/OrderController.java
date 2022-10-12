package com.struninproject.onlinestore.controller;

import com.struninproject.onlinestore.model.Order;
import com.struninproject.onlinestore.model.User;
import com.struninproject.onlinestore.repository.CategoryRepository;
import com.struninproject.onlinestore.repository.ManufacturerRepository;
import com.struninproject.onlinestore.repository.OrderRepository;
import com.struninproject.onlinestore.repository.ProductCountRepository;
import com.struninproject.onlinestore.repository.ProductRepository;
import com.struninproject.onlinestore.repository.UserRepository;
import com.struninproject.onlinestore.service.OrderService;
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
 * The {@code OrderController} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Controller
@RequestMapping(path = "/order")
public class OrderController {
    private final OrderRepository repository;
    private final OrderService service;

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductCountRepository productCountRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ManufacturerRepository manufacturerRepository;
    @Autowired
    CategoryRepository categoryRepository;

//    @PostConstruct
//    public void addOrders() {
//        final Order order = new Order();
//        final User user = new User();
//        user.setFirstName("test");
//        user.setGender(Gender.MALE);
//        userRepository.save(user);
//        order.setUser(user);
//        order.setCreated(LocalDateTime.now());
//        final Product product = new Product();
//        repository.save(order);
//        final Manufacturer manufacturer = new Manufacturer();
//        manufacturerRepository.save(manufacturer);
//        final Category category = new Category();
//        categoryRepository.save(category);
//        product.setCategory(category);
//        product.setManufacturer(manufacturer);
//        productRepository.save(product);
//        final ProductOrder productCount = new ProductOrder();
//        productCount.setOrder(order);
//        productCount.setProduct(product);
//        productCountRepository.save(productCount);
//    }

    @Autowired
    public OrderController(OrderRepository repository, OrderService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("/new")
    public ModelAndView showsRegistrationForm(ModelAndView modelAndView) {
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("user/new");
        return modelAndView;
    }

    @PostMapping("/new")
    public ModelAndView addUser(User user, ModelAndView modelAndView) {
//        repository.save(user);
//        service.createUser(user);
        modelAndView.setViewName("redirect:/order");
        return modelAndView;
    }

    @GetMapping()
    public ModelAndView getAllUsers(ModelAndView modelAndView) {
        modelAndView.addObject("orders", repository.findAll());
        modelAndView.setViewName("order/all");
        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(ModelAndView modelAndView, @PathVariable("id") String id) {
        modelAndView.addObject("orders", repository.findById(id).get());// FIXME: 03.10.2022
        modelAndView.setViewName("order/edit");
        return modelAndView;
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") Order order, @PathVariable("id") String id) {
        repository.findById(id);
        if (repository.existsById(id)) {
            repository.save(order);
        }
        return "redirect:/order";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id) {
        repository.deleteById(id);
        return "redirect:/order";
    }
}
