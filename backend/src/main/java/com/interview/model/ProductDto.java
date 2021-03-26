package com.interview.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.interview.entity.ProductEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {

    public ProductDto(ProductEntity entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        quantity = entity.getQuantity();
        rating = entity.getRating();
    }

    private Long id;
    @NotBlank(message = "Name is mandatory")
    @Size(max = 50)
    private String name;
    private String description;
    @Min(value = 0, message = "Quantity can not be negative")
    private int quantity;
    @Min(value = 0, message = "Rating can not be negative")
    private double rating;
}
