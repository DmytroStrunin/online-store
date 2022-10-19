package com.struninproject.onlinestore.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.util.Set;

/**
 * The {@code Category} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @Column(name = "category_id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String name;
    //Features
//    @ManyToOne
//    @JoinColumn(name = "parent_category_id")
//    private Category parentCategory;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "features", joinColumns = @JoinColumn(name = "category_id"))
    private Set<String> features; // FIXME: 03.10.2022
}
