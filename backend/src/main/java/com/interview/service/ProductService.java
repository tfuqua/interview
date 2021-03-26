package com.interview.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.interview.entity.ProductEntity;
import com.interview.exception.ProductNotFoundException;
import com.interview.model.ProductDto;
import com.interview.repository.ProductRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductDto> get(){
        List<ProductEntity> entities = productRepository.findAll();

        return entities.stream().map(e -> new ProductDto(e)).collect(Collectors.toList());
    }

    public ProductDto get(Long id){
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return new ProductDto(entity);
    }

    public ProductDto create(ProductDto dto) {
        ProductEntity entity = new ProductEntity(dto);
        ProductEntity savedEntity = productRepository.save(entity);

        return new ProductDto(savedEntity);
    }

    public ProductDto update(ProductDto dto) {
        productRepository.findById(dto.getId())
                .orElseThrow(() -> new ProductNotFoundException(dto.getId()));

        ProductEntity updatedEntity = new ProductEntity(dto);
        ProductEntity savedEntity = productRepository.save(updatedEntity);

        return new ProductDto(savedEntity);
    }

    public void delete(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        productRepository.deleteById(id);
    }
}
