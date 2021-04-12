package com.interview.resource;

import com.fasterxml.jackson.annotation.JsonView;
import com.interview.dto.CarShopDTO;
import com.interview.dto.Views;
import com.interview.service.CarShopService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/carshops")
@Validated
public class CarShopResource {
    private final CarShopService service;

    public CarShopResource(CarShopService service) {
        this.service = service;
    }

    @ApiOperation(value = "Retrieve CarShop by ID")
    @GetMapping("/{id}")
    @JsonView(Views.Details.class)
    public ResponseEntity<CarShopDTO> getCarShop(@PathVariable Long id) {
        return ResponseEntity.ok(service.getCarShop(id));
    }

    @ApiOperation(value = "Retrieving all CarShops in a paginated manner. Sorting criteria and paging criteria are customisable")
    @GetMapping
    @JsonView(Views.List.class)
    public ResponseEntity<Page<CarShopDTO>> getAllCarShops(Pageable pageable) {
        return ResponseEntity.ok(service.getCarShops(pageable));
    }

    @ApiOperation(value = "Creates a new CarShop entry")
    @PostMapping
    public ResponseEntity<?> createCarShop(@RequestBody @Valid CarShopDTO carShop, UriComponentsBuilder locationBuilder) {
        URI location = locationBuilder.path("/api/carshops/{id}")
                .buildAndExpand(service.addCarShop(carShop).getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Updates existing CarShop identified by ID")
    @PutMapping("/{id}")
    public ResponseEntity<CarShopDTO> updateCarShop(@PathVariable Long id, @RequestBody @Valid CarShopDTO carShop) {
        return ResponseEntity.ok(service.updateCarShop(id, carShop));
    }

    @ApiOperation(value = "Deletes a CarShop entry if exists, identified by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarShop(@PathVariable Long id) {
        service.deleteCarShop(id);
        return ResponseEntity.ok().build();
    }
}