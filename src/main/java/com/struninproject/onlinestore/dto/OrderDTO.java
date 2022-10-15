package com.struninproject.onlinestore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

/**
 * The {@code OrderDTO} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
public class OrderDTO {
    private BigDecimal totalPrice;
    private Set<ProductDTO> products;
}