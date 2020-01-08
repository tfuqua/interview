package com.interview.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SampleObjectDto {
    @NotNull
    @NotBlank
    private String name;
    private String description;
}
