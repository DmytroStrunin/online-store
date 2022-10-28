package com.struninproject.onlinestore.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The {@code Manufacturer} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Getter
@Setter
@Entity
@Table(name = "manufacturers")
public class Manufacturer extends AbstractEntity {
    private String name;
    private String country;
    private String image;
}
