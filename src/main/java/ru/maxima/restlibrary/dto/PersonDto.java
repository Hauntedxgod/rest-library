package ru.maxima.restlibrary.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.maxima.restlibrary.models.Book;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {



    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

    @Min(value = 1 , message = "Age should be more than 1")
    private Integer age;

    private String email;

    @Min(value = 11 , message = "phoneNumber not found")
    private String phoneNumber;

    @Size(min = 8 , message = "Min 8 chars , max 15 chars")
    private String password;

    private String role;


}
