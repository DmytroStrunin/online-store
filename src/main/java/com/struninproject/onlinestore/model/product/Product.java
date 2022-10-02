package com.struninproject.onlinestore.model.product;

import com.struninproject.onlinestore.model.Manufacturer;
import com.struninproject.onlinestore.model.ProductCount;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Set;

/**
 * The {@code Product} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Getter
@Setter
@Entity
@Table(name = "products")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    protected String id;
    protected String name;
    protected BigDecimal price;
    protected String image;
    protected String description;
    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    protected Manufacturer manufacturer;
    @OneToMany(mappedBy = "product")
    protected Set<ProductCount> productCounts;
}
