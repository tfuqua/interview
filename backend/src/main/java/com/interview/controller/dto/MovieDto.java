package com.interview.controller.dto;

import com.interview.model.Genre;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MovieDto {

    private Long id;

    private String title;

    private Long year;

    private List<Genre> genres;

}
