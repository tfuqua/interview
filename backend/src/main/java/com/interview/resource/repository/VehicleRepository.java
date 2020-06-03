package com.interview.resource.repository;

import com.interview.resource.model.Vehicle;
import org.springframework.data.repository.CrudRepository;

public interface VehicleRepository extends CrudRepository<Vehicle, Long> { }
