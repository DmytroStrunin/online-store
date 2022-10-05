package com.struninproject.onlinestore.model.enums;

import org.springframework.security.core.GrantedAuthority;

/**
 * The {@code Role} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
public enum Role implements GrantedAuthority {
    USER("User"),
    MANAGER("Manager"),
    ADMIN("Admin");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
