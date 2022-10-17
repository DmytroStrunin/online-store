package com.struninproject.onlinestore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * The {@code ProductDTO} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
//@NoArgsConstructor
public class ProductDTO {
    private String id;
    private String name;
    private BigDecimal price;
    private String image;
    private int quantity;
}