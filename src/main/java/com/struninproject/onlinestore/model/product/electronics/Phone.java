package com.struninproject.onlinestore.model.product.electronics;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The {@code Phone} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
@Getter
@Setter
@Entity
@Table(name = "phones")
public class Phone extends Electronics {
    private String communicationStandard;
}
