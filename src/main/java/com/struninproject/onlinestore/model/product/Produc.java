package com.struninproject.onlinestore.model.product;

import com.struninproject.onlinestore.model.Manufacturer;
import com.struninproject.onlinestore.model.ProductOrder;

import java.math.BigDecimal;
import java.util.Set;

/**
 * The {@code Product} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
//@Getter
//@Setter
//@Entity
//@Table(name = "products")
//@ToString // FIXME: 05.10.2022
//@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Produc {
//    @Id
//    @Column(name = "product_id")
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    protected String id;
    protected String name;
    protected BigDecimal price;
    protected String image;
    protected String description;
//    @ManyToOne
//    @JoinColumn(name = "manufacturer_id")
    protected Manufacturer manufacturer;
//    @ToString.Exclude // FIXME: 05.10.2022
//    @OneToMany(mappedBy = "product")
    protected Set<ProductOrder> orders;
}
