package com.struninproject.onlinestore.controller;

import com.struninproject.onlinestore.dto.OrderDTO;
import com.struninproject.onlinestore.dto.ProductDTO;
import com.struninproject.onlinestore.model.Order;
import com.struninproject.onlinestore.model.Product;
import com.struninproject.onlinestore.model.ProductOrder;
import com.struninproject.onlinestore.model.User;
import com.struninproject.onlinestore.model.enums.Status;
import com.struninproject.onlinestore.repository.CategoryRepository;
import com.struninproject.onlinestore.repository.ManufacturerRepository;
import com.struninproject.onlinestore.repository.OrderRepository;
import com.struninproject.onlinestore.repository.ProductOrderRepository;
import com.struninproject.onlinestore.repository.ProductRepository;
import com.struninproject.onlinestore.repository.UserRepository;
import com.struninproject.onlinestore.service.OrderService;
import com.struninproject.onlinestore.service.ProductOrderService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@code OrderController} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Controller
@RequestMapping(path = "/order")
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderService service;

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductOrderRepository productOrderRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ManufacturerRepository manufacturerRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductOrderService productOrderService;
    @Autowired
    OrderService orderService;
//    @PostConstruct
//    }

    @Autowired
    public OrderController(OrderRepository orderRepository, OrderService service) {
        this.orderRepository = orderRepository;
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


    @PostMapping("/add")
    public @ResponseBody
    OrderDTO addCart(@AuthenticationPrincipal User user, @RequestParam String productId) {
        final Product product = productRepository
                .findById(productId)
                .orElseThrow(IllegalArgumentException::new);  // FIXME: 15.10.2022

        final Order order = orderRepository
                .findOrderByUserAndStatus(user, Status.CART)
                .orElseGet(() -> orderService.createAndSave(user));

        final ProductOrder productOrder = productOrderRepository
                .findProductOrderByOrderAndProduct(order, product)
                .orElseGet(productOrderService::createAndSave);

        productOrder.setOrder(order);
        productOrder.setProduct(product);

        final Set<ProductOrder> productOrders = order.getProductOrders();

        int quantity = productOrder.getQuantity();
        productOrder.setQuantity(++quantity);
        productOrders.add(productOrder);

        productOrderRepository.save(productOrder);

        return loadCart(user);
    }

    @GetMapping("/load")
    public @ResponseBody
    OrderDTO loadCart(@AuthenticationPrincipal User user) {
        final Order order = orderRepository
                .findOrderByUserAndStatus(user, Status.CART)
                .orElseGet(() -> orderService.createAndSave(user));

        Set<ProductDTO> products = new HashSet<>(); // FIXME: 15.10.2022
        BigDecimal totalPrice = new BigDecimal("0");
        for (ProductOrder prOrder : order.getProductOrders()) {
            final Product pr = prOrder.getProduct();
            totalPrice = totalPrice.add(pr.getPrice().multiply(new BigDecimal(prOrder.getQuantity())));
            products.add(new ProductDTO(pr.getId(), pr.getName(), pr.getPrice(), pr.getImage(), prOrder.getQuantity()));
        }
        return new OrderDTO(totalPrice, products);
    }

    @SneakyThrows
    @PostMapping("/del")
    public @ResponseBody
    OrderDTO delCart(@AuthenticationPrincipal User user, @RequestParam String productId) {
        final Product product = productRepository
                .findById(productId)
                .orElseThrow(IllegalArgumentException::new);  // FIXME: 15.10.2022
        Order order = orderRepository
                .findOrderByUserAndStatus(user, Status.CART)
                .orElseThrow(IllegalArgumentException::new);
        final ProductOrder productOrder = productOrderRepository
                .findProductOrderByOrderAndProduct(order, product)
                .orElseThrow(IllegalArgumentException::new);

        if (productOrder.getQuantity() > 1) {
            productOrder.setQuantity(productOrder.getQuantity() - 1);
            order.getProductOrders().add(productOrder); // FIXME: 17.10.2022
        } else {
            order.getProductOrders().remove(productOrder);
        }

        orderRepository.save(order);
        return loadCart(user);
    }

    @GetMapping("/buy")
    public @ResponseBody
    OrderDTO buyCart(@AuthenticationPrincipal User user) {
        orderRepository
                .findOrderByUserAndStatusAndProductOrdersNotNull(user, Status.CART)
                .ifPresent((o) -> {
                    o.setCreated(LocalDateTime.now());
                    o.setStatus(Status.IN_PROGRESS);
                    orderRepository.save(o);
                });
        return loadCart(user);
    }

    @GetMapping()
    public ModelAndView getAllUsers(ModelAndView modelAndView) {
        modelAndView.addObject("orders", orderRepository.findAll());
        modelAndView.setViewName("order/all");
        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(ModelAndView modelAndView, @PathVariable("id") String id) {
        modelAndView.addObject("orders", orderRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new));
        modelAndView.setViewName("order/edit");
        return modelAndView;
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") Order order, @PathVariable("id") String id) {
        orderRepository.findById(id);
        if (orderRepository.existsById(id)) {
            orderRepository.save(order);
        }
        return "redirect:/order";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id) {
        orderRepository.deleteById(id);
        return "redirect:/order";
    }
}
