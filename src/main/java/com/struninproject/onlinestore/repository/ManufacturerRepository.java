package com.struninproject.onlinestore.repository;

import com.struninproject.onlinestore.model.Manufacturer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The {@code ManufacturerRepository} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Repository
public interface ManufacturerRepository extends CrudRepository<Manufacturer, String> {
}
