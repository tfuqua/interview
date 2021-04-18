package com.interview.controller.dto;

import java.math.BigDecimal;

public class UpdateProductDto extends InsertProductDto {
    public UpdateProductDto(String name, String category, String subCategory, String imageUrl, String productUrl, String manufacturer, String description, BigDecimal price) {
        super(name, category, subCategory, imageUrl, productUrl, manufacturer, description, price);
    }
}
