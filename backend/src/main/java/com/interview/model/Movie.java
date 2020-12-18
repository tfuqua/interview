package com.interview.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        indexes = {@Index(name = "idx_movie_year", columnList = "year")},
        uniqueConstraints = {@UniqueConstraint(name = "unq_movie_title_year", columnNames = {"title", "year"})}
)
public class Movie {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private Long year;

    @Builder.Default
    @Convert(converter = GenreListConverter.class)
    private List<Genre> genres = new ArrayList<>();

}
