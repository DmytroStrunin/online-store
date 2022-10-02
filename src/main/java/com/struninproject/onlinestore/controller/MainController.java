package com.struninproject.onlinestore.controller;

import com.struninproject.onlinestore.model.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * The {@code MainController} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Controller
@RequestMapping(path = "/party")
public class MainController {

    @GetMapping("/new")
    public ModelAndView newUser(ModelAndView modelAndView) {
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("index1");
        return modelAndView;
    }













    @PostMapping(path = "/p")
    public String findGroupByName(@RequestParam String name, Model model) {
        return "findGroupByName";
    }


}
