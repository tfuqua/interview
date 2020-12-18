package com.interview.controller.dto;

import com.interview.model.Genre;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class UpdateMovieDto {

    @NotNull
    private String title;

    @NotNull
    @Min(1900)
    @Max(2500)
    private Long year;

    @Builder.Default
    private List<Genre> genres = new ArrayList<>();

}
