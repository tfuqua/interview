package com.interview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.interview.model.CarEntity;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {}
