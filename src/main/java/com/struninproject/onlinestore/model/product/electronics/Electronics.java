package com.struninproject.onlinestore.model.product.electronics;

import com.struninproject.onlinestore.model.product.Produc;

import java.math.BigDecimal;

/**
 * The {@code Electronics} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
//@Getter
//@Setter
//@Entity
//@Table(name = "electronics")
//@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Electronics extends Produc {
    private BigDecimal screenDiagonal;
    private String operatingSystem;
    private String processor;
    private int ramSize;
    private int hddSize;
    private int battery;
}
