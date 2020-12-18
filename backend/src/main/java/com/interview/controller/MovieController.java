package com.interview.controller;

import com.interview.controller.dto.MovieDto;
import com.interview.controller.dto.UpdateMovieDto;
import com.interview.model.Movie;
import com.interview.repository.MovieSpecification;
import com.interview.service.MovieService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public MovieDto create(@Valid @RequestBody UpdateMovieDto dto) {
        Movie movie = movieService.create(dto);

        return getDto(movie);
    }

    @PutMapping("/{id}")
    public MovieDto update(@PathVariable Long id, @Valid @RequestBody UpdateMovieDto dto) {
        Movie movie = movieService.update(id, dto);

        return getDto(movie);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        movieService.delete(id);
    }

    @GetMapping("/{id}")
    public MovieDto get(@PathVariable Long id) {
        Movie movie = movieService.get(id);

        return getDto(movie);
    }

    @GetMapping("/filter")
    public List<MovieDto> filter(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long year,
            MovieSpecification specification) {

        return movieService.filter(specification).stream()
                .map(this::getDto)
                .collect(Collectors.toList());
    }

    private MovieDto getDto(Movie movie) {
        return MovieDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .year(movie.getYear())
                .genres(movie.getGenres())
                .build();
    }

}
