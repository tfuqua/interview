package com.interview.employee.controller.payload;

import com.interview.core.payload.ResponsePayload;
import com.interview.entity.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
public class EmployeeListResponsePayload implements ResponsePayload {
    private String email;
    private String firstName;
    private LocalDate hireDate;
    private Long id;
    private String lastName;

    public EmployeeListResponsePayload(Employee employee) {
        this.email = employee.getEmail();
        this.firstName = employee.getFirstName();
        this.hireDate = employee.getHireDate();
        this.id = employee.getId();
        this.lastName = employee.getLastName();
    }
}
