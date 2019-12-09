package com.interview.service;


import com.interview.model.CarEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.interview.repository.CarRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CarService {

	@Autowired
	CarRepository repository;

	/**
	 * Get a single car 
	 * @param id
	 * @return
	 */
	public CarEntity getCar(Long id) throws Exception  {
		CarEntity entity = null;
		try {
			Optional<CarEntity> car = repository.findById(id);
			if(car.isPresent()) {
				 entity =  car.get();
			} else {
				throw new Exception("Record not found");
			}

		} catch (Exception e) {}
		
		return entity;
	}

	/**
	 * Get all cars 
	 * @return
	 */

	public List<CarEntity> getAllCars() throws Exception {
		List<CarEntity> carList = repository.findAll();
		return (carList.size() != 0 )? carList:new ArrayList<CarEntity>();
	} 

	/**
	 * Add a single car 
	 * @param entity
	 * @return
	 */

	public CarEntity insertCar(CarEntity entity) {		
		entity = repository.save(entity);
		return entity;
	}

	/**
	 * Update a single car 
	 * @param entity
	 * @return
	 * @throws Exception
	 */

	public CarEntity updateCar(CarEntity entity) throws Exception {

		Optional<CarEntity> car = repository.findById(entity.getId());

		try {
			if(car.isPresent()) {
				CarEntity updateCar = car.get();
				updateCar.setCarBrand(entity.getCarBrand());
				updateCar.setCarModel(entity.getCarModel());
				updateCar.setCarColor(entity.getCarColor());
				entity = repository.save(updateCar);
			} else {
				throw new Exception("Record not found");
			}

		} catch (Exception e) {}
		return entity;
	}

	/**
	 * Delete a single car
	 * @param id
	 * @throws Exception
	 */

	public void deleteCar(Long id) throws Exception {
		Optional<CarEntity> car = repository.findById(id);
		try {
			if(car.isPresent()) {
				 repository.deleteById(id);
			} else {
				throw new Exception("Record not found");
			}

		} catch (Exception e) {}
	}
}
