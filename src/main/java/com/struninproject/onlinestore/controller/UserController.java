package com.struninproject.onlinestore.controller;

import com.struninproject.onlinestore.model.user.Gender;
import com.struninproject.onlinestore.model.user.Role;
import com.struninproject.onlinestore.model.user.User;
import com.struninproject.onlinestore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;

/**
 * The {@code UserController} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Controller
@RequestMapping(path = "/user")
public class UserController {
    private final UserRepository service;

    @Autowired
    public UserController(UserRepository service) {
        this.service = service;
    }

    @PostConstruct
    public void addUsers(){
        User user = new User();
        user.setAge(2);
        user.setEmail("admin@bla.com");
        user.setGender(Gender.FEMALE);
        user.setFirstName("First");
        user.setLastName("Last");
        user.setRole(Role.ADMIN);
        user.setPassword("admin");
        service.save(user);
        User user1 = new User();
        user1.setAge(2);
        user1.setEmail("user@bla.com");
        user1.setGender(Gender.FEMALE);
        user1.setFirstName("First");
        user1.setLastName("Last");
        user1.setRole(Role.USER);
        user1.setPassword("user");
        service.save(user1);
    }

    @GetMapping("/new")
    public ModelAndView showsRegistrationForm(ModelAndView modelAndView){
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("user/new");
        return modelAndView;
    }

    @PostMapping("/new")
    public ModelAndView addUser(User user, ModelAndView modelAndView){
        service.save(user);
        modelAndView.setViewName("redirect:/user/users");
        return modelAndView;
    }
    @GetMapping("/users")
    public ModelAndView getAllUsers(ModelAndView modelAndView){
        modelAndView.addObject("users", service.findAll());
        modelAndView.setViewName("user/users");
        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(ModelAndView modelAndView, @PathVariable("id") String id) {
        modelAndView.addObject("user", service.findById(id).get());// FIXME: 03.10.2022 
        modelAndView.setViewName("user/edit");
        return modelAndView;
    }

//    @GetMapping("/{id}/edit")
//    public String edit(Model model, @PathVariable("id") String id) {
//        model.addAttribute("user", service.findById(id));
//        return "user/edit";
//    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") String id) {
        service.findById(id);
        return "redirect:/user/users";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id) {
        service.deleteById(id);
        return "redirect:/user/users";
    }


    @GetMapping("/")
    public ModelAndView testNavbar(ModelAndView modelAndView){
        modelAndView.setViewName("navbar");
        return modelAndView;
    }
}
