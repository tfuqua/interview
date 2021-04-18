package com.interview.repository;

import com.interview.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ProductSearchRepository {

    private final ProductRepository repository;

    public ProductSearchRepository(ProductRepository repository) {
        this.repository = repository;
    }

    public Page<Product> searchProducts(String searchValue, Pageable pageMetadata) {
        return repository.findAll((searchValue == null || searchValue.isEmpty()) ? null :
                LikeSpecification.<Product>of("name", searchValue).
        or(
                LikeSpecification.of("category", searchValue)).
        or(
                LikeSpecification.of("subCategory", searchValue)).
        or(
                LikeSpecification.of("manufacturer", searchValue)), pageMetadata);
    }
}
