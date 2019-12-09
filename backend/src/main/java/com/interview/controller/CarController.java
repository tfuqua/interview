package com.interview.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import com.interview.model.CarEntity;
import com.interview.service.CarService;

@RestController
@RequestMapping("api/v1/cars")
public class CarController {

	@Autowired
	CarService carService;


	@DeleteMapping("/{id}")
	public HttpStatus deleteEmployeeById(@PathVariable("id") Long id) throws Exception {
		carService.deleteCar(id);
		return HttpStatus.OK;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CarEntity> getCar(@PathVariable("id") Long id) throws Exception {
		CarEntity entity = carService.getCar(id);
		return new ResponseEntity<CarEntity>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<CarEntity>> getAllCars() throws Exception {
		List<CarEntity> cars = carService.getAllCars();
		return new ResponseEntity<List<CarEntity>>(cars, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<CarEntity> insertCar(@RequestBody CarEntity car) {
		CarEntity add = carService.insertCar(car);
		return new ResponseEntity<CarEntity>(add, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping("/update")
	public ResponseEntity<CarEntity> updateCar(@RequestBody CarEntity car) throws Exception {
		CarEntity added = carService.updateCar(car);
		return new ResponseEntity<CarEntity>(added, new HttpHeaders(), HttpStatus.OK);
	}

}
