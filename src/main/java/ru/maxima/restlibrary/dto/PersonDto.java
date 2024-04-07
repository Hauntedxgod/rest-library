package ru.maxima.restlibrary.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class PersonDto {


    private String name;

    private Integer age;

    private String email;

    private String phoneNumber;

    private String password;
}
