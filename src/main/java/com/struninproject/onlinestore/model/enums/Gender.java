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
    OTHER("Other");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
