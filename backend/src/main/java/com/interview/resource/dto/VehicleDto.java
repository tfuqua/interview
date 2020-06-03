package com.interview.resource.dto;

import lombok.Data;

@Data
public class VehicleDto {
    private Long id;
    private String model;
    private Integer mileage;
    private Integer year;
    private Double price;
}
