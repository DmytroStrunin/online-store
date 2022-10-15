package com.struninproject.onlinestore.model.enums;

/**
 * The {@code Status} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
public enum Status {
    CART("Cart"),
    IN_PROGRESS("In progress"),
    PAID("Paid"),
    COMPLETED("Completed");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}