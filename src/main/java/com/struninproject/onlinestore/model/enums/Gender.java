package com.struninproject.onlinestore.model.enums;

/**
 * The {@code Gender} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    MIDDLE("Middle");

    private final String value;

    private Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
