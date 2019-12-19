package com.interview.employee.repository;

import com.interview.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e WHERE (:name is null or (lower(e.firstName) like lower(concat('%', :name,'%'))) or (lower(e.lastName) like lower(concat('%', :name,'%')))) and e.status =com.interview.entity.enums.RecordStatus.ACTIVE")
    Page<Employee> filterActiveEmployees(String name, Pageable pageable);

    @Query("SELECT e FROM Employee e WHERE e.status = com.interview.entity.enums.RecordStatus.ACTIVE and e.id=:id")
    Optional<Employee> findActiveUserById(@Param("id") Long id);

    @Query("SELECT e FROM Employee e WHERE e.status = com.interview.entity.enums.RecordStatus.ACTIVE and e.email=:email")
    Optional<Employee> findActiveUserByEmail(@Param("email") String email);
}