package com.struninproject.onlinestore.controller;

import com.struninproject.onlinestore.dto.ProductDTO;
import com.struninproject.onlinestore.model.Order;
import com.struninproject.onlinestore.model.User;
import com.struninproject.onlinestore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
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


    @PutMapping("/add")
    public @ResponseBody
    Set<ProductDTO> addItemToCart(@AuthenticationPrincipal User user,
                            @RequestParam String productId) {
        orderService.addProductInCart(user, productId);
        return orderService.getUserCart(user);
    }

    @PutMapping("/del")
    public @ResponseBody
    Set<ProductDTO> delItemFromCart(@AuthenticationPrincipal User user,
                            @RequestParam String productId) {
        orderService.removeProductInCart(user, productId);
        return orderService.getUserCart(user);
    }

    @PutMapping("/buy")
    public @ResponseBody
    Set<ProductDTO> buyItemsInCart(@AuthenticationPrincipal User user) {
        orderService.updateUserCartStatus(user);
        return orderService.getUserCart(user);
    }

    @GetMapping("/load")
    public @ResponseBody
    Set<ProductDTO> loadItemsFromCart(@AuthenticationPrincipal User user) {
        return orderService.getUserCart(user);
    }

    @GetMapping()
    public ModelAndView getAllUsers(ModelAndView modelAndView) {
        modelAndView.addObject("orders", orderService.findAll());
        modelAndView.setViewName("order/all");
        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(ModelAndView modelAndView,
                             @PathVariable("id") String id) {
        modelAndView.addObject("orders", orderService.findById(id));
        modelAndView.setViewName("order/edit");
        return modelAndView;
    }

    @PatchMapping("/update")
    public String update(@ModelAttribute("order") Order order) {
        orderService.update(order);
        return "redirect:/order";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id) {
        orderService.deleteById(id);
        return "redirect:/order";
    }
}
