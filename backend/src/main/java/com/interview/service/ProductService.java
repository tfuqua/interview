package com.interview.service;

import com.interview.controller.dto.InsertProductDto;
import com.interview.controller.dto.UpdateProductDto;
import com.interview.entity.Product;
import com.interview.exception.BusinessRuleViolationException;
import com.interview.exception.ResourceNotFoundException;
import com.interview.repository.ProductRepository;
import com.interview.repository.ProductSearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final ProductSearchRepository queryRepository;

    public ProductService(ProductRepository repository, ProductSearchRepository queryRepository) {
        this.repository = repository;
        this.queryRepository = queryRepository;
    }

    @Transactional(readOnly = true)
    public Page<Product> search(String searchValue, Pageable pageMetadata) {
        return queryRepository.searchProducts(searchValue, pageMetadata);
    }

    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return findProduct(id);
    }

    @Transactional
    public Product create(InsertProductDto dto) {
        isProductUnique(dto);
        Product product = new Product(dto.getName(), dto.getCategory(), dto.getSubCategory(), dto.getImageUrl(), dto.getProductUrl(), dto.getManufacturer(), dto.getDescription(), dto.getPrice());
        return repository.save(product);
    }

    @Transactional
    public Product update(Long id, UpdateProductDto dto) {
        Product product = findProduct(id);
        isProductUnique(dto, product.getId());
        updateProduct(dto, product);
        /* The changes would be reflected in the database even if the following line does not exists, because it is attached object.
           However, as a best practice, save operation is still called in order to isolate service layer from different repository behaviors.*/
        return repository.save(product);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(findProduct(id));
    }

    private Product findProduct(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Product.class, id));
    }

    private void isProductUnique(UpdateProductDto dto, Long id) {
        Optional<Product> product = repository.findByNameAndCategoryAndSubCategoryIgnoreCase(dto.getName(), dto.getCategory(), dto.getSubCategory());
        if(product.isPresent() && product.get().getId() != id) {
            throw new BusinessRuleViolationException(MessageBundle.SHOULD_BE_UNIQUE_AFTER_UPDATE, dto.getName(), dto.getCategory(), dto.getSubCategory());
        }
    }

    private void isProductUnique(InsertProductDto dto) {
        if(repository.existsByNameAndCategoryAndSubCategoryIgnoreCase(dto.getName(),dto.getCategory(), dto.getSubCategory())) {
            throw new BusinessRuleViolationException(MessageBundle.SHOULD_BE_UNIQUE, dto.getName(), dto.getCategory(), dto.getSubCategory());
        }
    }

    private void updateProduct(UpdateProductDto dto, Product product) {
        product.setName(dto.getName());
        product.setCategory(dto.getCategory());
        product.setSubCategory(dto.getSubCategory());
        product.setImageUrl(dto.getImageUrl());
        product.setProductUrl(dto.getProductUrl());
        product.setManufacturer(dto.getManufacturer());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
    }
}
