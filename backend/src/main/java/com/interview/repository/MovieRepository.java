package com.interview.repository;

import com.interview.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Boolean existsByTitle(String title);
}
