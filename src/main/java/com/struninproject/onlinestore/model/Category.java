package com.struninproject.onlinestore.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.util.List;

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
public class Category extends AbstractEntity {
    private String name;
    //Feature
//    @ManyToOne
//    @JoinColumn(name = "child_category_id")
//    private Category childCategory;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "features", joinColumns = @JoinColumn(name = "category_id"))
    private List<String> features;
}
