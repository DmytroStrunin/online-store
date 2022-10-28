package com.struninproject.onlinestore.controller.impl;

import com.struninproject.onlinestore.model.User;
import com.struninproject.onlinestore.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * The {@code UserController} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Controller
@RequestMapping(path = "/user")
public class UserController extends AbstractController<User, UserService> {

    @Autowired
    public UserController(UserService service) {
        super(service);
    }

    @PostMapping("/login")
    public ModelAndView addUser(@Valid User user,
                                BindingResult result,
                                ModelAndView modelAndView){
        if (result.hasErrors()) {
            modelAndView.setViewName("login");
            return modelAndView;
        }
        service.save(user);
        modelAndView.setViewName("redirect:/user");
        return modelAndView;
    }

    @PostMapping("/registration")
    public ModelAndView registrationUser(@Valid User user,
                                         BindingResult result,
                                         ModelAndView modelAndView){
        if (result.hasErrors() || service.save(user) == null) {     // FIXME: 28.10.2022
            modelAndView.setViewName("login");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/user");
        return modelAndView;
    }
}
