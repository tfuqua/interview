package com.interview.employee.controller;

import com.interview.core.payload.PaginatedResponsePayload;
import com.interview.employee.controller.payload.EmployeeFilterRequestPayload;
import com.interview.employee.controller.payload.EmployeeListResponsePayload;
import com.interview.employee.controller.payload.EmployeeUpsertRequestPayload;
import com.interview.employee.service.EmployeeService;
import com.interview.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(@Autowired EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/{id}")
    public EmployeeListResponsePayload getEmployee(@PathVariable("id") Long id) {
        return new EmployeeListResponsePayload(employeeService.findActiveEmployee(id));
    }

    @GetMapping
    public PaginatedResponsePayload<Employee, EmployeeListResponsePayload> filterEmployees(
            EmployeeFilterRequestPayload filterRequestPayload,
            @PageableDefault(size = 20)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "firstName", direction = Sort.Direction.ASC),
                    @SortDefault(sort = "lastName", direction = Sort.Direction.ASC)}) Pageable pageable) {
        Page<Employee> page = employeeService.filterActiveEmployees(filterRequestPayload, pageable);

        return new PaginatedResponsePayload<>(page, EmployeeListResponsePayload::new);
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
