package com.interview.employee.controller.payload;

import com.interview.core.payload.RequestPayload;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeFilterRequestPayload implements RequestPayload {
    private String employee;
}
