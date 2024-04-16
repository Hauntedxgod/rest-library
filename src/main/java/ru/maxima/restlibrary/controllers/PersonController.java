package ru.maxima.restlibrary.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maxima.restlibrary.dto.BookDto;
import ru.maxima.restlibrary.dto.BookIdDto;
import ru.maxima.restlibrary.dto.PersonDto;
import ru.maxima.restlibrary.exceptions.PersonNotFoundException;
import ru.maxima.restlibrary.models.Book;
import ru.maxima.restlibrary.models.Person;
import ru.maxima.restlibrary.service.BookService;
import ru.maxima.restlibrary.service.PersonService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService service;

    private final BookService bookService;
    private final ModelMapper modelMapper;


    @Autowired
    public PersonController(PersonService service, BookService bookService, ModelMapper modelMapper) {
        this.service = service;
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/persons/{id}")
    public PersonDto getPerson(@PathVariable Long id ) throws PersonNotFoundException {
        return modelMapper.map(service.findById(id) , PersonDto.class);
    }


    @PostMapping("/created")
    public ResponseEntity<HttpStatus> createPerson(@RequestBody @Valid PersonDto personDto  ,
                                                   @AuthenticationPrincipal UserDetails userDetails,
                                                   BindingResult bindingResult){

        service.checkErrors(bindingResult);

        Person person = modelMapper.map(personDto , Person.class);
        service.changesPerson(person , userDetails.getUsername());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePerson(@PathVariable("id") Long id ,
                                                   @AuthenticationPrincipal UserDetails userDetails){

        service.deletePerson(id, userDetails.getUsername());
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @PostMapping("/take/{id}")
    public ResponseEntity<HttpStatus> takeBook(@PathVariable("id") Long id , BookDto bookDto){
        service.takeBook(id , bookDto);
        return ResponseEntity.ok(HttpStatus.OK);
//        Метод взятие книги
    }


}
