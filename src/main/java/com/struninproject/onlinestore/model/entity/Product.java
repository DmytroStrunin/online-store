package com.struninproject.onlinestore.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Map;
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
public class Product extends AbstractEntity {
    private String name;
    private BigDecimal price;
    private String image;
    private String description;
    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;
    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    private Set<ProductOrder> productOrders;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ElementCollection
    @CollectionTable(name = "specifications",
            joinColumns = {@JoinColumn(name = "product_id"
                    , referencedColumnName = "id"
            )})
    @MapKeyColumn(name = "feature")
    @Column(name = "value")
    private Map<String, String> specifications;
}
