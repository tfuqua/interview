package com.interview.controller;

import com.interview.assembler.CarModelAssembler;
import com.interview.entity.Car;
import com.interview.model.CarDto;
import com.interview.request.CarPostRequest;
import com.interview.request.CarPutRequest;
import com.interview.service.CarService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping(path = "/api/cars")
public class CarsController {
    @Autowired
    private CarService carService;
    @Autowired
    private CarModelAssembler assembler;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<CarDto>>> getAllCars(){
        List<CarDto> cars = carService.getAllCars().stream().map(car -> modelMapper.map(car, CarDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok(assembler.toCollectionModel(cars));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CarDto>> getCar(@PathVariable("id") Long id){
        Car car = carService.getCarById(id);
        CarDto carResponse = modelMapper.map(car, CarDto.class);
        return ResponseEntity.ok(assembler.toModel(carResponse));
    }

    @PostMapping
    public ResponseEntity<EntityModel<CarDto>> createCar(@Valid @RequestBody CarPostRequest car){
        Car carRequest = modelMapper.map(car, Car.class);
        Car c = carService.createCar(carRequest);
        CarDto carResponse = modelMapper.map(c, CarDto.class);
        EntityModel<CarDto> entityModel = assembler.toModel(carResponse);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<CarDto>> updateCar(@Valid @RequestBody CarPutRequest car, @PathVariable("id") int id){
        Car carRequest = modelMapper.map(car, Car.class);
        Car c = carService.updateCar(id, carRequest);
        CarDto carResponse = modelMapper.map(c, CarDto.class);
        EntityModel<CarDto> entityModel = assembler.toModel(carResponse);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCar(@PathVariable("id") long id){
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }




}
