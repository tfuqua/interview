package com.interview.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import com.interview.model.ProductDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PRODUCTS")
@Getter
@Setter
@NoArgsConstructor
public class ProductEntity {

    public ProductEntity(ProductDto dto) {
        id = dto.getId();
        name = dto.getName();
        description = dto.getDescription();
        quantity = dto.getQuantity();
        rating = dto.getRating();
    }

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private int quantity;
    private double rating;
}
