package com.struninproject.onlinestore.service.impl;

import com.struninproject.onlinestore.model.User;
import com.struninproject.onlinestore.model.enums.Role;
import com.struninproject.onlinestore.repository.impl.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

/**
 * The {@code UserService} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */

@Service
public class UserService extends AbstractService<User, UserRepository> {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        super(repository);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User create() {
        return new User();
    }

    public User save(User user) {
        user.setActive(true);
        user.setRoles(new HashSet<>());
        user.getRoles().add(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        log.info("Saving new User with email: {}", userEmail);
        repository.save(user);
        return user;
    }

    public String validateIfExistUser(User user) {
        String message = "";
        if (repository.findByEmail(user.getEmail()) != null) {
            message=String.format("User with email: %s already exists.", user.getEmail());
        }
        return message;
    }
}
