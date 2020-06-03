package com.interview.resource.service;

import com.interview.resource.dto.VehicleDto;
import com.interview.resource.model.Vehicle;
import com.interview.resource.repository.VehicleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private VehicleRepository vehicleRepository;
    private ModelMapper vehicleMapper;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, ModelMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
    }

    public VehicleDto create(VehicleDto vehicleDto) {

        if (checkUserExits(vehicleDto.getId()))
            throw new EntityExistsException(String.format("Vehicle with id %s already exits", vehicleDto.getId()));

        Vehicle vehicle = vehicleMapper.map(vehicleDto, Vehicle.class);

        return vehicleMapper.map(vehicleRepository.save(vehicle), VehicleDto.class);
    }

    private boolean checkUserExits(Long id) {
        if (id == null)
            return false;

        return vehicleRepository.findById(id).isPresent();
    }

    public VehicleDto edit(VehicleDto vehicleDto) {
        Vehicle vehicle = getVehicle(vehicleDto.getId());
        vehicleMapper.map(vehicleDto, vehicle);

        return vehicleMapper.map(vehicleRepository.save(vehicle), VehicleDto.class);
    }

    private Vehicle getVehicle(Long id) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);

        if (vehicle.isEmpty()) {
            throw new EntityNotFoundException(String.format("There is no vehicle with id: %s", id));
        }

        return vehicle.get();
    }

    public VehicleDto get(Long id) {
        return vehicleMapper.map(getVehicle(id), VehicleDto.class);
    }

    public List<VehicleDto> getAll() {
        List<VehicleDto> vehicles = new ArrayList<>();
        vehicleRepository.findAll().forEach(vehicle -> {
            vehicles.add(vehicleMapper.map(vehicle, VehicleDto.class));
        });

        return vehicles;
    }

    public void delete(Long id) {
        vehicleRepository.deleteById(id);
    }
}
