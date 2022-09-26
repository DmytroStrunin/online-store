package com.struninproject.onlinestore.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * The {@code Order} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Data
@Entity
@Table(name = "oorder")
public class Order {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private LocalDateTime created;
}
