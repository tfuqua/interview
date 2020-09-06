package com.interview.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "USER")
public class User{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Email must not be empty")
    @Email(message = "Email must be a valid email address")
    @Column(name = "EMAIL_ID", unique = true, nullable = false)
    private String emailId;

    @Column(name = "FIRST_NAME")
    @NotNull(message = "First name must not be empty")
    private String firstName;

    @Column(name = "LAST_NAME")
    @NotNull(message = "Last name must not be empty")
    private String lastName;

    @Column
    private String phoneNumber;

    @Column
    private String website;

}

