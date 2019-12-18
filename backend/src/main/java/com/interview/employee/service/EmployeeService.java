package com.interview.employee.service;

import com.interview.core.payload.exception.ResourceNotFoundException;
import com.interview.employee.repository.EmployeeRepository;
import com.interview.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeService(@Autowired EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional(readOnly = true)
    public Employee findActiveEmployee(Long id) {
        Optional<Employee> user = employeeRepository.findActiveUserById(id);

        Employee active = user.orElseThrow(()->new ResourceNotFoundException(("Employee not found")));

        return active;
    }
}
