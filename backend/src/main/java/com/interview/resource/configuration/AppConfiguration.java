package com.interview.resource.configuration;

import com.interview.resource.dto.VehicleDto;
import com.interview.resource.model.Vehicle;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean("vehicleMapper")
    public ModelMapper vehicleMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Vehicle.class, VehicleDto.class);
        modelMapper.typeMap(VehicleDto.class, Vehicle.class);

        return modelMapper;
    }
}
