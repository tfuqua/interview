package com.interview.service;

import com.interview.entity.Car;

import java.util.List;

public interface CarService {
    List<Car> getAllCars();

    Car createCar(Car car);

    Car updateCar(long id, Car car);

    void deleteCar(long id);

    Car getCarById(long id);
}
