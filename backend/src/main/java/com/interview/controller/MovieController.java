package com.interview.controller;

import com.interview.entity.Movie;
import com.interview.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/movies")
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies(){
        return ResponseEntity.ok(service.getAllMovies());
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long movieId){
        return ResponseEntity.ok(service.getMovie(movieId));
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie newMovie){
        return ResponseEntity.ok(service.addMovie(newMovie));
    }

    @PutMapping("/{movieId}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long movieId, @RequestBody Movie updatedMovie){
        return ResponseEntity.ok(service.updateMovie(movieId, updatedMovie));
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<Movie> deleteMovieById(@PathVariable Long movieId){
        service.deleteMovie(movieId);
        return ResponseEntity.ok().build();
    }


}
