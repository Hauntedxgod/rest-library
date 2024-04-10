package ru.maxima.restlibrary.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {


    private String name;

    private Integer yearOfProduction;

    private String author;

    private String annotation;
}
