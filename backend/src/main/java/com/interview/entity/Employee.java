package com.interview.entity;

import com.interview.entity.enums.RecordStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue
    private Long id;
    @Column(columnDefinition = "varchar", length = 100)
    private String email;
    @Column(columnDefinition = "varchar", length = 30)
    private String firstName;
    @Column(columnDefinition = "varchar", length = 30)
    private String lastName;
    private LocalDate hireDate;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar", length = 8)
    private RecordStatus status;
    @Version
    private int version;
}