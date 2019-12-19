package com.interview.employee.repository;

import com.interview.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e WHERE e.status = com.interview.entity.enums.RecordStatus.ACTIVE and e.id=:id")
    Optional<Employee> findActiveUserById(@Param("id") Long id);

    @Query("SELECT e FROM Employee e WHERE e.status = com.interview.entity.enums.RecordStatus.ACTIVE and e.email=:email")
    Optional<Employee> findActiveUserByEmail(@Param("email") String email);
}