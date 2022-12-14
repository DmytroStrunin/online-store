package com.struninproject.onlinestore.model.entity;

import com.struninproject.onlinestore.model.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
public class Order extends AbstractEntity {
    private LocalDateTime created;
    private BigDecimal totalPrice;
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    Status status;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<ProductOrder> productOrders;
}
