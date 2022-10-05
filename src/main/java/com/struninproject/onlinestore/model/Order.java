package com.struninproject.onlinestore.model;

import com.struninproject.onlinestore.model.user.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * The {@code Order} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private LocalDateTime created;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
//    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, orphanRemoval= true)
//    @JoinColumn(name = "order_id")
//    private List<Product> products;
    @OneToMany(mappedBy = "order")
    private Set<ProductCount> products;
}
