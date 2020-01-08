package com.interview.repository;

import com.interview.model.SampleObject;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface SampleObjectRepository extends CrudRepository<SampleObject, Long> {
    @Override
    Set<SampleObject> findAll();
}
