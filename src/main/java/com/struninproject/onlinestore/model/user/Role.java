package com.struninproject.onlinestore.model.user;

/**
 * The {@code Role} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
public enum Role {
    GUEST("Guest"),
    USER("User"),
    MANAGER("Manager"),
    ADMIN("Admin");

    private final String value;

    private Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
