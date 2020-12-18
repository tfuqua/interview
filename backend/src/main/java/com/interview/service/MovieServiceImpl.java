package com.interview.service;

import com.interview.controller.dto.UpdateMovieDto;
import com.interview.model.Movie;
import com.interview.repository.MovieRepository;
import com.interview.repository.MovieSpecification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie create(UpdateMovieDto dto) {
        Movie movie = Movie.builder()
                .title(dto.getTitle())
                .year(dto.getYear())
                .genres(dto.getGenres())
                .build();

        return movieRepository.save(movie);
    }

    @Override
    public Movie update(Long id, UpdateMovieDto dto) {
        Movie movie = get(id);

        movie.setTitle(dto.getTitle());
        movie.setYear(dto.getYear());
        movie.setGenres(dto.getGenres());

        return movie;
    }

    @Override
    public void delete(Long id) {
        movieRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Movie get(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movie> filter(MovieSpecification specification) {
        return movieRepository.findAll(specification);
    }

}
