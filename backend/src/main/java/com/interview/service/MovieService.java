package com.interview.service;

import com.interview.entity.Movie;
import com.interview.repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository repository;

    public MovieService(MovieRepository repository){
        this.repository = repository;
    }

    @Transactional
    public Movie getMovie(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Can't find movie"));
    }

    @Transactional
    public List<Movie> getAllMovies() {
        return repository.findAll();
    }

    @Transactional
    public Movie addMovie(Movie newMovie){
        if(repository.existsByTitle(newMovie.getTitle())) throw new RuntimeException("Movie is already in Database!");
        return repository.save(new Movie(newMovie.getTitle(),newMovie.getBlurb(),newMovie.getRating(),newMovie.getGenre(),newMovie.getImageUrl()));
    }

    @Transactional
    public Movie updateMovie(Long movieId, Movie updatedMovie){
        if(repository.existsById(movieId)){
            Movie movie = repository.getOne(movieId);
            movie.setTitle(updatedMovie.getTitle());
            movie.setBlurb(updatedMovie.getBlurb());
            movie.setGenre(updatedMovie.getGenre());
            movie.setRating(updatedMovie.getRating());
            movie.setImageUrl(updatedMovie.getImageUrl());
            return repository.save(movie);
        }
        throw new RuntimeException("Movie is not in Database!");
    }

    @Transactional
    public void deleteMovie(long id){
        repository.deleteById(id);
    }

}
