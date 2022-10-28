package com.struninproject.onlinestore.service.impl;

import com.struninproject.onlinestore.model.Manufacturer;
import com.struninproject.onlinestore.repository.impl.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The {@code ManufacturerService} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Service
public class ManufacturerService extends AbstractService<Manufacturer, ManufacturerRepository> {

    @Autowired
    public ManufacturerService(ManufacturerRepository repository) {
        super(repository);
    }

    @Override
    public Manufacturer create() {
        return new Manufacturer();
    }
}
