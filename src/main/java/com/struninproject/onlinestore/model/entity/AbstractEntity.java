package com.struninproject.onlinestore.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * The {@code AbstractEntity} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntity{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
}