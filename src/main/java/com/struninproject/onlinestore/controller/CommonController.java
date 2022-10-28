package com.struninproject.onlinestore.controller;

import com.struninproject.onlinestore.model.entity.AbstractEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * The {@code CommonController} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
public interface CommonController<E extends AbstractEntity> {

    @GetMapping("/new")
    ModelAndView showsNewFormPage(ModelAndView modelAndView);

    @PostMapping("/new")
    ModelAndView createNew(E entity, ModelAndView modelAndView);

    @GetMapping("/all")
    ModelAndView showAllPage(ModelAndView modelAndView);

    @GetMapping("/{id}/edit")
    ModelAndView showEditPage(ModelAndView modelAndView, @PathVariable("id") String id);

    @PatchMapping("/update")
    String update(@ModelAttribute("category") E entity);

    @DeleteMapping("/{id}")
    String delete(@PathVariable("id") String id);
}
