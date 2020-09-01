package com.interview.controller.payload;

import com.interview.common.RequestPayload;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpsertBookPayload implements RequestPayload {
    @NotBlank
    @Size(min = 2, max = 120)
    private String name;
    @NotBlank
    @Size(min = 2, max = 100)
    private String authorName;
    @NotBlank
    @Size(min = 2, max = 100)
    private String authorSurname;
    @URL
    private String imageUrl;
    @NotNull
    private LocalDate publishDate;
}
