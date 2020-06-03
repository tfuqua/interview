package com.interview.resource.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Vehicle {

    @Id
    private Long id;
    private String model;
    private Integer mileage;
    private Integer year;
    private Double price;
}
