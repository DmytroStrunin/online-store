package com.struninproject.onlinestore.controller;

import com.struninproject.onlinestore.model.User;
import com.struninproject.onlinestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/new")
    public ModelAndView showsRegistrationForm(ModelAndView modelAndView){
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("user/new");
        return modelAndView;
    }

    @PostMapping("/new")
    public ModelAndView addUser(@Valid User user, BindingResult result, ModelAndView modelAndView){
        if (result.hasErrors()) {
            modelAndView.setViewName("login");
            return modelAndView;
        }
        userService.createUser(user);
        modelAndView.setViewName("redirect:/user");
        return modelAndView;
    }

    @PostMapping("/registration")
    public ModelAndView registrationUser(@Valid User user, BindingResult result, ModelAndView modelAndView){
        if (result.hasErrors()) {
            modelAndView.setViewName("login");
            return modelAndView;
        }
        userService.createUser(user);
        modelAndView.setViewName("redirect:/user");
        return modelAndView;
    }

    @GetMapping()
    public ModelAndView getAllUsers(ModelAndView modelAndView){
        modelAndView.addObject("users", userService.findAll());
        modelAndView.setViewName("user/all");
        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(ModelAndView modelAndView, @PathVariable("id") String id) {
        modelAndView.addObject("user", userService.findById(id));
        modelAndView.setViewName("user/edit");
        return modelAndView;
    }

    @PatchMapping("/update")
    public String update(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/user";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id) {
        userService.deleteById(id);
        return "redirect:/user";
    }
}
