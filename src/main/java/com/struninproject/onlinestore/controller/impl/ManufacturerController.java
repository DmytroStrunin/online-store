package com.struninproject.onlinestore.controller.impl;

import com.struninproject.onlinestore.model.entity.Manufacturer;
import com.struninproject.onlinestore.service.impl.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * The {@code ManufacturerController} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Controller
@RequestMapping(path = "/manufacturer")
public class ManufacturerController
        extends AbstractController<Manufacturer, ManufacturerService> {

    @Autowired
    public ManufacturerController(ManufacturerService service) {
        super(service);
    }
}
