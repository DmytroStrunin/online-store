package com.struninproject.onlinestore.service;

import com.struninproject.onlinestore.model.User;
import com.struninproject.onlinestore.model.enums.Role;
import com.struninproject.onlinestore.repository.UserRepository;
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
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository=repository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean createUser(User user) {
        String userEmail = user.getEmail();
        if (repository.findByEmail(userEmail) != null) return false;
        user.setActive(true);
        user.setRoles(new HashSet<>());
        user.getRoles().add(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        log.info("Saving new User with email: {}", userEmail);
        repository.save(user);
        return true;
    }

    public Iterable<User> findAll() {
        return repository.findAll();
    }

    public User findById(String id) {
        return repository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    public void update(User user) {
        if (repository.existsById(user.getId())){
            repository.save(user);
        }
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
