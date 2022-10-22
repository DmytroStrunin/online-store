package com.struninproject.onlinestore.controller;

import com.struninproject.onlinestore.model.Manufacturer;
import com.struninproject.onlinestore.service.ManufacturerService;
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

/**
 * The {@code ManufacturerController} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Controller
@RequestMapping(path = "/manufacturer")
public class ManufacturerController {
    private final ManufacturerService manufacturerService;

    @Autowired
    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping("/new")
    public ModelAndView showsNewItemForm(ModelAndView modelAndView) {
        modelAndView.addObject("manufacturer", new Manufacturer());
        modelAndView.setViewName("manufacturer/new");
        return modelAndView;
    }

    @PostMapping("/new")
    public ModelAndView addNewItem(Manufacturer manufacturer, ModelAndView modelAndView) {
        manufacturerService.save(manufacturer);
        modelAndView.setViewName("redirect:/manufacturer");
        return modelAndView;
    }

    @GetMapping
    public ModelAndView getAllItems(ModelAndView modelAndView) {
        modelAndView.addObject("manufacturers", manufacturerService.findAll());
        modelAndView.setViewName("manufacturer/all");
        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(ModelAndView modelAndView, @PathVariable("id") String id) {
        modelAndView.addObject("manufacturer", manufacturerService.findById(id));
        modelAndView.setViewName("manufacturer/edit");
        return modelAndView;
    }

    @PatchMapping("/update")
    public String update(@ModelAttribute("manufacturer") Manufacturer manufacturer) {
        manufacturerService.update(manufacturer);
        return "redirect:/manufacturer";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id) {
        manufacturerService.deleteById(id);
        return "redirect:/manufacturer";
    }
}
