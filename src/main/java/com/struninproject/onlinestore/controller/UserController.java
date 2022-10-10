package com.struninproject.onlinestore.controller;

import com.struninproject.onlinestore.model.User;
import com.struninproject.onlinestore.model.enums.Gender;
import com.struninproject.onlinestore.model.enums.Role;
import com.struninproject.onlinestore.repository.UserRepository;
import com.struninproject.onlinestore.service.UserService;
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

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * The {@code UserController} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Controller
@RequestMapping(path = "/user")
public class UserController {
    private final UserRepository repository;
    private final UserService service;

    @Autowired
    public UserController(UserRepository repository, UserService service) {
        this.repository = repository;
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
        user.setRoles(Set.of(Role.ADMIN));
        user.setPassword("admin");
        repository.save(user);
        User user1 = new User();
        user1.setAge(2);
        user1.setEmail("user@bla.com");
        user1.setGender(Gender.FEMALE);
        user1.setFirstName("First");
        user1.setLastName("Last");
        user1.setRoles(Set.of(Role.USER));
        user1.setPassword("user");
        repository.save(user1);
    }

    @GetMapping("/new")
    public ModelAndView showsRegistrationForm(ModelAndView modelAndView){
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("user/new");
        return modelAndView;
    }

    @PostMapping("/new")
    public ModelAndView addUser(User user, ModelAndView modelAndView){
//        repository.save(user);
        service.createUser(user);
        modelAndView.setViewName("redirect:/user");
        return modelAndView;
    }
    @GetMapping()
    public ModelAndView getAllUsers(ModelAndView modelAndView){
        modelAndView.addObject("users", repository.findAll());
        modelAndView.setViewName("user/all");
        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(ModelAndView modelAndView, @PathVariable("id") String id) {
        modelAndView.addObject("user", repository.findById(id).get());// FIXME: 03.10.2022
        modelAndView.setViewName("user/edit");
        return modelAndView;
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") String id) {
        repository.findById(id);
        if (repository.existsById(id)){
            repository.save(user);
        }
        return "redirect:/user";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id) {
        repository.deleteById(id);
        return "redirect:/user";
    }
}
