package com.interview.test;

import com.interview.controller.dto.UpdateMovieDto;
import com.interview.model.Genre;
import com.interview.model.Movie;
import com.interview.service.MovieService;
import com.interview.test.core.IntegrationTestCase;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;

public class MoviesIntegrationTest extends IntegrationTestCase {

    @Autowired
    private MovieService movieService;

    @Test
    public void whenFilteredWithNullSpecification_thenAllItemsAreReturned() {
        movieService.create(newMovie("Comedy", 2005l, Genre.COMEDY, Genre.THRILLER));
        movieService.create(newMovie("War", 1995l, Genre.ROMANCE));

        Assert.assertEquals(2l, movieService.filter(null).size());
    }

    @Test
    public void canUpdateMovie() {
        Movie movie = movieService.create(newMovie("Comedy", 2005l, Genre.COMEDY, Genre.THRILLER));
        Long movieId = movie.getId();

        UpdateMovieDto dto = newMovie("Comedy'", 2000l, Genre.DOCUMENTARY);
        movieService.update(movieId, dto);

        Movie updatedMovie = movieService.get(movieId);

        Assert.assertEquals(dto.getTitle(), updatedMovie.getTitle());
        Assert.assertTrue(updatedMovie.getYear().equals(dto.getYear()));
        Assert.assertTrue(updatedMovie.getGenres().containsAll(dto.getGenres()));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void cannotAddSameTitleSameYearMovies() {
        movieService.create(newMovie("Comedy", 2005l, Genre.COMEDY, Genre.THRILLER));
        movieService.create(newMovie("Comedy", 2005l, Genre.ROMANCE));
    }

    private UpdateMovieDto newMovie(String title, Long year, Genre... genres) {
        return UpdateMovieDto.builder()
                .title(title)
                .year(year)
                .genres(Arrays.asList(genres.clone()))
                .build();
    }

}
