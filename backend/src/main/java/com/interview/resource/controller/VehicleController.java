package com.interview.resource.controller;

import com.interview.resource.dto.VehicleDto;
import com.interview.resource.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/vehicle")
public class VehicleController {

    private VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public VehicleDto create(@RequestBody VehicleDto vehicleDto) {
        return vehicleService.create(vehicleDto);
    }

    @GetMapping("/{id}")
    public VehicleDto get(@PathVariable("id") Long id) {
        return vehicleService.get(id);
    }

    @GetMapping("/all")
    public List<VehicleDto> getAll() {
        return vehicleService.getAll();
    }

    @PutMapping
    public VehicleDto edit(@RequestBody VehicleDto vehicleDto) {
        return vehicleService.edit(vehicleDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        vehicleService.delete(id);
    }
}
