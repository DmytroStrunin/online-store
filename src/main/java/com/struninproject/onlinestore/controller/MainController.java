package com.struninproject.onlinestore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The {@code MainController} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Controller
@RequestMapping(path = "/party")
public class MainController {

    @PostMapping(path = "/p")
    public String findGroupByName(@RequestParam String name, Model model) {
        return "findGroupByName";
    }
}
