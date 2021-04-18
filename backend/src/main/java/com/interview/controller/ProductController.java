package com.interview.controller;

import com.interview.controller.dto.InsertProductDto;
import com.interview.controller.dto.PagedProductListDto;
import com.interview.controller.dto.ProductDto;
import com.interview.controller.dto.UpdateProductDto;
import com.interview.entity.Product;
import com.interview.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<PagedProductListDto> search(@RequestParam(name = "searchValue", required = false) String searchValue, @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int limit) {
        Pageable pageMetadata = PageRequest.of(pageNumber, limit);
        Page<Product> pagedProduct = service.search(searchValue, pageMetadata);
        List<ProductDto> dtoList = pagedProduct.getContent().stream().map(ProductDto::new).collect(Collectors.toList());
        return new ResponseEntity(new PagedProductListDto(dtoList, pagedProduct.getTotalElements(), pagedProduct.getSize(), pagedProduct.getNumber()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> get(@PathVariable Long id) {
        Product product = service.findById(id);
        return new ResponseEntity(new ProductDto(product), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDto> create(@Valid @RequestBody InsertProductDto dto) {
        Product product = service.create(dto);
        return new ResponseEntity(new ProductDto(product), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable Long id, @Valid @RequestBody UpdateProductDto dto) {
        Product product = service.update(id, dto);
        return new ResponseEntity(new ProductDto(product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
