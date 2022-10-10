package com.struninproject.onlinestore.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
 * The {@code Item} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Getter
@Setter
@Entity
@Table(name = "products")
//@ToString // FIXME: 05.10.2022
@Inheritance(strategy = InheritanceType.JOINED)
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String name;
    private BigDecimal price;
    private String image;
    private String description;
    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;
    @OneToMany(mappedBy = "product")
    private Set<ProductOrder> productOrders;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ElementCollection
    @CollectionTable(name = "specifications",
            joinColumns = {@JoinColumn(name = "product_id"
//                    , referencedColumnName = "id"
            )})
    @MapKeyColumn(name = "feature")
    @Column(name = "value")
    private Map<String, String> specifications;
}
