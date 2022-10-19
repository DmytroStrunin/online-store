package com.struninproject.onlinestore.dto;

import com.struninproject.onlinestore.model.enums.Gender;
import com.struninproject.onlinestore.model.enums.Role;
import lombok.Builder;

import java.util.Set;

/**
 * The {@code UserDTO} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Builder
public class UserDTO {
    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private int age;
    private Gender gender;
    private Set<Role> roles;
}
