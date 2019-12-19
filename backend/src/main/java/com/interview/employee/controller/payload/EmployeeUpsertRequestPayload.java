package com.interview.employee.controller.payload;

import com.interview.core.payload.RequestPayload;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeUpsertRequestPayload implements RequestPayload {
    @Size(min=1, max = 100, message = "Email should exist and be at most 30 characters")
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;
    @NotNull(message = "First Name cannot be null")
    @Size(min=1, max = 30, message = "First Name should exist and be at most 30 characters")
    private String firstName;
    @NotNull(message = "Last Name cannot be null")
    @Size(min=1, max = 30, message = "Last Name should exist and be at most 30 characters")
    private String lastName;
    @NotNull(message = "Hire date cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate hireDate;
}
