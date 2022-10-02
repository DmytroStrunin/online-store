package com.struninproject.onlinestore.service;

import com.struninproject.onlinestore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The {@code UserService} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Service
public class UserService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository=repository;
    }
}
