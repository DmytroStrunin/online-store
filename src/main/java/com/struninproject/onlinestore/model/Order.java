package com.struninproject.onlinestore.model;

import com.struninproject.onlinestore.model.enums.Status;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
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
    private BigDecimal totalPrice;
//    @ManyToOne
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)// FIXME: 08.10.2022
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    Status status;
//    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, orphanRemoval= true)
//    @JoinColumn(name = "order_id")
//    private List<Product> products;
    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<ProductOrder> productOrders;
}
