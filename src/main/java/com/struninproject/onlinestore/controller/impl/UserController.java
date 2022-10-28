package com.struninproject.onlinestore.controller.impl;

import com.struninproject.onlinestore.model.User;
import com.struninproject.onlinestore.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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

    @PostMapping("/registration")
    public ModelAndView registrationUser(@Valid User user,
                                         BindingResult result,
                                         ModelAndView modelAndView){
        final String err = service.validateIfExistUser(user);
        if (!err.isEmpty()) {
            ObjectError error = new ObjectError("email", err);
            result.addError(error);
        }
        if (result.hasErrors()) {
            modelAndView.setViewName("home");
            return modelAndView;
        }
        service.save(user);
        modelAndView.setViewName("redirect:/user");
        return modelAndView;
    }
}
