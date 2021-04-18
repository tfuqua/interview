package com.interview.repository;

import com.interview.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Optional<Product> findByNameAndCategoryAndSubCategoryIgnoreCase(String name, String category, String subCategory);
    Boolean existsByNameAndCategoryAndSubCategoryIgnoreCase(String name, String category, String subCategory);
}
