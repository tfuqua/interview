package com.interview.repository;

import com.interview.entity.CarShop;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarShopRepository extends PagingAndSortingRepository<CarShop, Long> {
}