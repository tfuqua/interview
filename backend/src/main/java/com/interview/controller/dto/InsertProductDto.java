package com.interview.controller.dto;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

public class InsertProductDto {

    @NotBlank
    @Size(min = 3, max = 256)
    private String name;

    @NotBlank
    @Size(min = 3, max = 256)
    private String category;

    @NotBlank
    @Size(min = 3, max = 256)
    private String subCategory;

    @URL
    private String imageUrl;

    @URL
    private String productUrl;

    @NotBlank
    @Size(min = 2, max = 256)
    private String manufacturer;

    @Size(min = 2, max = 2048)
    private String description;

    @DecimalMin("0.01")
    private BigDecimal price;

    public InsertProductDto(String name, String category, String subCategory, String imageUrl, String productUrl, String manufacturer, String description, BigDecimal price) {
        this.name = name;
        this.category = category;
        this.subCategory = subCategory;
        this.imageUrl = imageUrl;
        this.productUrl = productUrl;
        this.manufacturer = manufacturer;
        this.description = description;
        this.price = price;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InsertProductDto that = (InsertProductDto) o;
        return Objects.equals(name, that.name) && Objects.equals(category, that.category) && Objects.equals(subCategory, that.subCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category, subCategory);
    }
}
