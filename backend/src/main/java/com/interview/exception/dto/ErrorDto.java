package com.interview.exception.dto;

import java.time.LocalDate;

public class ErrorDto {
    private String[] message;
    private String code;

    public ErrorDto(String code, String... message) {
        this.message = message;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String[] getMessage() {
        return message;
    }

    public void setMessage(String[] message) {
        this.message = message;
    }
}

