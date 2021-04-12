package com.interview.service;

import com.interview.dto.CarShopDTO;
import com.interview.entity.CarShop;
import com.interview.repository.CarShopRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CarShopService {

    private final CarShopRepository repository;
    private final ModelMapper mapperService;

    public CarShopService(CarShopRepository repository, ModelMapper mapperService) {
        this.repository = repository;
        this.mapperService = mapperService;
    }

    @Transactional(readOnly = true)
    public CarShopDTO getCarShop(Long id) {
        return repository.findById(id)
                .map(carShop -> mapperService.map(carShop, CarShopDTO.class))
                .orElseThrow(() -> new IllegalArgumentException("cannot find car shop by id"));
    }

    @Transactional(readOnly = true)
    public Page<CarShopDTO> getCarShops(Pageable pageable) {
        return repository.findAll(pageable)
                .map(carShop -> mapperService.map(carShop, CarShopDTO.class));
    }

    public CarShopDTO addCarShop(CarShopDTO newCarShop) {
        CarShop carShop = mapperService.map(newCarShop, CarShop.class);
        return mapperService.map(repository.save(carShop), CarShopDTO.class);
    }

    public CarShopDTO updateCarShop(Long id, CarShopDTO carShopToUpdate) {
        return repository.findById(id)
                .map(carShop -> {
                    carShop.setName(carShopToUpdate.getName());
                    carShop.setDescription(carShopToUpdate.getDescription());
                    carShop.setSummary(carShopToUpdate.getSummary());
                    carShop.setLogoUrl(carShopToUpdate.getLogoUrl());
                    return mapperService.map(carShop, CarShopDTO.class);
                })
                .orElseThrow(() -> new IllegalArgumentException("cannot update non existing car shop"));
    }

    public void deleteCarShop(Long id) {
        CarShopDTO existingShop = getCarShop(id);
        repository.deleteById(existingShop.getId());
    }
}