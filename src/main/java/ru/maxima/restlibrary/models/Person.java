package ru.maxima.restlibrary.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "person")
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "Name should not to be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Column(name = "name")
    private String name;

    @Min(value = 1 , message = "Age should be more than 1")
    @Column(name = "age")
    private Integer age;

    @Column(name = "email")
    private String email;

    @Column(name = "phoneNumber")
    private String phoneNumber;


    @Size(min = 8 , message = "Min 8 chars , max 15 chars")
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "created_At")
    private LocalDateTime createdAt;

    @Column(name = "removed_At")
    private LocalDateTime removedAt;

    @Column(name = "created_Person")
    private String createdPerson;

    @Column(name = "removed_Person")
    private String removedPerson;

}
