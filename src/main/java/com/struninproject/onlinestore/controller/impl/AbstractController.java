package com.struninproject.onlinestore.controller.impl;

import com.struninproject.onlinestore.controller.CommonController;
import com.struninproject.onlinestore.model.entity.AbstractEntity;
import com.struninproject.onlinestore.service.CommonService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * The {@code AbstractController} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
public abstract class AbstractController<E extends AbstractEntity,
        S extends CommonService<E>>
        implements CommonController<E> {

    private final E e;
    protected final S service;

    protected AbstractController(S service) {
        this.service = service;
        this.e = service.create();
    }

    @GetMapping("/new")
    public ModelAndView showsNewFormPage(ModelAndView modelAndView) {
        modelAndView.addObject("data", service.create());
        modelAndView.setViewName(String.format("%s/new",
                e.getClass()
                        .getSimpleName()
                        .toLowerCase()));
        return modelAndView;
    }

    @PostMapping("/new")
    public ModelAndView createNew(E entity,
                                  ModelAndView modelAndView) {
        service.save(entity);
        modelAndView.setViewName(String.format("redirect:/%s/all",
                e.getClass()
                        .getSimpleName()
                        .toLowerCase()));
        return modelAndView;
    }

    @GetMapping("/all")
    public ModelAndView showAllPage(ModelAndView modelAndView) {
        modelAndView.addObject("data", service.findAll());
        modelAndView.setViewName(String.format("%s/all",
                e.getClass()
                        .getSimpleName()
                        .toLowerCase()));
        return modelAndView;
    }


    @GetMapping("/{id}/edit")
    public ModelAndView showEditPage(ModelAndView modelAndView, @PathVariable("id") String id) {
        modelAndView.addObject("data", service.findById(id));
        modelAndView.setViewName(String.format("%s/edit",
                e.getClass()
                        .getSimpleName()
                        .toLowerCase()));
        return modelAndView;
    }

    @PatchMapping("/update")
    public String update(@ModelAttribute("category") E entity) {
        service.update(entity);
        return String.format("redirect:/%s/all",
                e.getClass()
                        .getSimpleName()
                        .toLowerCase());
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id) {
        service.deleteById(id);
        return String.format("redirect:/%s/all",
                e.getClass()
                        .getSimpleName()
                        .toLowerCase());
    }
}