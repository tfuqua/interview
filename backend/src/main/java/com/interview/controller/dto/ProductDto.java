package com.interview.controller.dto;

import com.interview.entity.Product;

import java.math.BigDecimal;

public class ProductDto {
    private Long id;
    private String name;
    private String category;
    private String subCategory;
    private String imageUrl;
    private String productUrl;
    private String manufacturer;
    private String description;
    private BigDecimal price;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.category = product.getCategory();
        this.subCategory = product.getSubCategory();
        this.imageUrl = product.getImageUrl();
        this.productUrl = product.getProductUrl();
        this.manufacturer = product.getManufacturer();
        this.description = product.getDescription();
        this.price = product.getPrice();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
