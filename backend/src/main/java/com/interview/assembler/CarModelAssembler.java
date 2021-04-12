package com.interview.assembler;

import com.interview.controller.CarsController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.interview.model.CarDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarModelAssembler implements RepresentationModelAssembler<CarDto, EntityModel<CarDto>>{
    @Override
    public EntityModel<CarDto> toModel(CarDto entity) {

        return EntityModel.of(entity,
                linkTo(methodOn(CarsController.class).getCar(entity.getId())).withSelfRel(),
                linkTo(methodOn(CarsController.class).getAllCars()).withRel("cars"));
    }

    @Override
    public CollectionModel<EntityModel<CarDto>> toCollectionModel(Iterable<? extends CarDto> entities) {
        List<EntityModel<CarDto>> list = new ArrayList<>();

        entities.forEach(e -> list.add(toModel(e)) );
        //.collect(Collectors.toList());
        return CollectionModel.of(list, linkTo(methodOn(CarsController.class).getAllCars()).withSelfRel());

    }
}
