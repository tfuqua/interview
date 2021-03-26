package com.interview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.interview.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

}
