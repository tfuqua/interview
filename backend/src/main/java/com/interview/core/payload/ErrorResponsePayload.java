package com.interview.core.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponsePayload implements ResponsePayload {
    private List<String> errors;

    public ErrorResponsePayload(String error) {
        this(Arrays.asList(error));
    }
}
