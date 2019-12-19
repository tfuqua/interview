package com.interview.employee.controller;

import com.interview.employee.controller.payload.EmployeeListResponsePayload;
import com.interview.employee.controller.payload.EmployeeUpsertRequestPayload;
import com.interview.employee.service.EmployeeService;
import com.interview.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeListResponsePayload createUser(@Valid @RequestBody EmployeeUpsertRequestPayload employeeUpsertRequestPayload) {
        Employee employee = employeeService.createEmployee(employeeUpsertRequestPayload);

        return new EmployeeListResponsePayload(employee);
    }

    @PutMapping(value = "/{id}")
    public EmployeeListResponsePayload updateEmployee(@PathVariable("id") Long id, @Valid @RequestBody EmployeeUpsertRequestPayload employeeUpsertRequestPayload) {
        Employee employee = employeeService.updateEmployee(id, employeeUpsertRequestPayload);

        return new EmployeeListResponsePayload(employee);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
    }
}
