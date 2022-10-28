package com.struninproject.onlinestore.repository.impl;

import com.struninproject.onlinestore.model.User;
import com.struninproject.onlinestore.repository.CommonRepository;
import org.springframework.stereotype.Repository;

/**
 * The {@code UserRepository} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Repository
public interface UserRepository extends CommonRepository<User> {

    User findByEmail(String email);
}
