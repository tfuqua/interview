package com.interview.service.impl;

import com.interview.exception.CarNotFoundException;
import com.interview.entity.Car;
import com.interview.repository.CarsRepository;
import com.interview.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarsRepository carsRepository;

    @Override
    public List<Car> getAllCars() {
        return (List<Car>) carsRepository.findAll();
    }

    @Override
    public Car createCar(Car car) {
        return carsRepository.save(car);
    }

    @Override
    public Car updateCar(long id, Car carReq) {
        Car car = carsRepository.findById(id).orElseThrow(() -> new CarNotFoundException(id));
        car.setModel(carReq.getModel());
        car.setPrice(carReq.getPrice());
        return carsRepository.save(car);
    }

    public void deleteCar(long id) {
        Car car = carsRepository.findById(id).orElseThrow(() -> new CarNotFoundException(id));
        carsRepository.delete(car);
    }

    public Car getCarById(long id) {
        return carsRepository.findById(id).orElseThrow(() -> new CarNotFoundException(id));
    }
}
