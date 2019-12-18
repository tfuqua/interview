package com.interview.employee.controller;

import com.interview.employee.controller.payload.EmployeeListResponsePayload;
import com.interview.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(@Autowired EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/{id}")
    public EmployeeListResponsePayload getEmployee(@PathVariable("id") Long id) {
        return new EmployeeListResponsePayload(employeeService.findActiveEmployee(id));
    }
}
