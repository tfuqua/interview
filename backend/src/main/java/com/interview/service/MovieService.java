package com.interview.service;

import com.interview.controller.dto.UpdateMovieDto;
import com.interview.model.Movie;
import com.interview.repository.MovieSpecification;

import java.util.List;

public interface MovieService {

    Movie create(UpdateMovieDto dto);

    Movie update(Long id, UpdateMovieDto dto);

    void delete(Long id);

    Movie get(Long id);

    List<Movie> filter(MovieSpecification specification);

}
