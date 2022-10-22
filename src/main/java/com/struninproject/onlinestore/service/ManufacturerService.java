package com.struninproject.onlinestore.service;

import com.struninproject.onlinestore.model.Manufacturer;
import com.struninproject.onlinestore.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The {@code ManufacturerService} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Service
public class ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    @Autowired
    public ManufacturerService(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    public void save(Manufacturer manufacturer) {
        manufacturerRepository.save(manufacturer);
    }

    public Iterable<Manufacturer> findAll() {
       return manufacturerRepository.findAll();
    }

    public Manufacturer findById(String id) {
        return manufacturerRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    public void update(Manufacturer manufacturer) {
        if (manufacturerRepository.existsById(manufacturer.getId())) {
            manufacturerRepository.save(manufacturer);
        }
    }

    public void deleteById(String id) {
        manufacturerRepository.deleteById(id);
    }
}
