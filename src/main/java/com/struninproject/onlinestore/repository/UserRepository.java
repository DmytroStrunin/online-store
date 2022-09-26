package com.struninproject.onlinestore.repository;

import com.struninproject.onlinestore.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The {@code UserRepository} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Repository
public interface UserRepository extends CrudRepository<User,String> {
}
