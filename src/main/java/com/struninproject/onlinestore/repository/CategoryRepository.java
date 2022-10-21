package com.struninproject.onlinestore.repository;

import com.struninproject.onlinestore.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The {@code CategoryRepository} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Repository
public interface CategoryRepository extends CrudRepository<Category,String> {

    Category findByName(String name);
}
