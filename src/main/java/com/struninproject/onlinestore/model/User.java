package com.struninproject.onlinestore.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The {@code User} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Data
@Entity
@Table(name = "uzer")
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String role;
}
