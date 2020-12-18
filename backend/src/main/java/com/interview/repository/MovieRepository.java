package com.interview.repository;

import com.interview.model.Movie;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
}
