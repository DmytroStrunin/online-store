package com.struninproject.onlinestore.repository;

import com.struninproject.onlinestore.model.user.User;
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
    User findByEmail(String email);
}
