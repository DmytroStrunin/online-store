package com.struninproject.onlinestore.controller.impl;

import com.struninproject.onlinestore.dto.ProductDTO;
import com.struninproject.onlinestore.model.Order;
import com.struninproject.onlinestore.model.User;
import com.struninproject.onlinestore.service.impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

/**
 * The {@code OrderController} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Controller
@RequestMapping(path = "/order")
public class OrderController extends AbstractController <Order, OrderService> {

    @Autowired
    public OrderController(OrderService service) {
        super(service);
    }

    @PatchMapping("/cart/add")
    public @ResponseBody
    Set<ProductDTO> addItemToCart(@AuthenticationPrincipal User user,
                                  @RequestParam String productId) {
        return service.addProductInCart(user, productId);
    }

    @PatchMapping("/cart/del")
    public @ResponseBody
    Set<ProductDTO> delItemFromCart(@AuthenticationPrincipal User user,
                                    @RequestParam String productId) {
        return service.removeProductFromCart(user, productId);
    }

    @PatchMapping("/cart/buy")
    public @ResponseBody
    Set<ProductDTO> buyItemsInCart(@AuthenticationPrincipal User user) {
        return service.updateUserCartStatus(user);
    }

    @GetMapping("/cart/load")
    public @ResponseBody
    Set<ProductDTO> loadItemsFromCart(@AuthenticationPrincipal User user) {
        return service.getUserCart(user);
    }
}
