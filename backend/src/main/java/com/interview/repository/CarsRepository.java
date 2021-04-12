package com.interview.repository;

import com.interview.entity.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarsRepository extends CrudRepository<Car, Long> {
}
