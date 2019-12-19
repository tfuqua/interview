package com.interview.employee.service;

import com.interview.core.payload.exception.ResourceNotFoundException;
import com.interview.employee.controller.payload.EmployeeFilterRequestPayload;
import com.interview.employee.controller.payload.EmployeeUpsertRequestPayload;
import com.interview.employee.exception.EmailInUseException;
import com.interview.employee.repository.EmployeeRepository;
import com.interview.entity.Employee;
import com.interview.entity.enums.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        Optional<Employee> employee = employeeRepository.findActiveUserById(id);

        Employee active = employee.orElseThrow(()->new ResourceNotFoundException(("Employee not found")));

        return active;
    }

    @Transactional(readOnly = true)
    public Page<Employee> filterActiveEmployees(EmployeeFilterRequestPayload filterRequestPayload, Pageable pageable) {
        return employeeRepository.filterActiveEmployees(filterRequestPayload.getEmployee(), pageable);
    }

    @Transactional
    public Employee createEmployee(EmployeeUpsertRequestPayload employeeUpsertRequestPayload) {
        checkEmailUniqueness(employeeUpsertRequestPayload, null);

        Employee employee = new Employee();

        employee.setStatus(RecordStatus.ACTIVE);

        return this.saveEmployee(employee, employeeUpsertRequestPayload);
    }

    @Transactional
    public Employee updateEmployee(Long id, EmployeeUpsertRequestPayload employeeUpsertRequestPayload) {
        checkEmailUniqueness(employeeUpsertRequestPayload, id);
        Optional<Employee> employee = employeeRepository.findActiveUserById(id);

        Employee active = employee.orElseThrow(()->new ResourceNotFoundException(("User not found")));

        return this.saveEmployee(active, employeeUpsertRequestPayload);
    }

    @Transactional
    public void deleteEmployee(Long id) {
        Optional<Employee> user = employeeRepository.findActiveUserById(id);

        Employee active = user.orElseThrow(()->new ResourceNotFoundException(("Employee not found")));
        active.setStatus(RecordStatus.PASSIVE.PASSIVE);

        employeeRepository.save(active);
    }

    private Employee saveEmployee(Employee employee, EmployeeUpsertRequestPayload employeeUpsertRequestPayload) {
        employee.setEmail(employeeUpsertRequestPayload.getEmail());
        employee.setFirstName(employeeUpsertRequestPayload.getFirstName());
        employee.setLastName(employeeUpsertRequestPayload.getLastName());
        employee.setHireDate(employeeUpsertRequestPayload.getHireDate());

        return employeeRepository.save(employee);
    }

    private void checkEmailUniqueness(EmployeeUpsertRequestPayload employeeUpsertRequestPayload, Long id) {
        Optional<Employee> employee = employeeRepository.findActiveUserByEmail(employeeUpsertRequestPayload.getEmail());

        if (!employee.isPresent()) {
            return;
        }

        if (id == null) {
            throw new EmailInUseException();
        }

        if (!employee.get().getId().equals(id)) {
            throw new EmailInUseException();
        }
    }
}
