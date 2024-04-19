package ru.maxima.restlibrary.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maxima.restlibrary.dto.BookDto;
import ru.maxima.restlibrary.dto.PersonDto;
import ru.maxima.restlibrary.exceptions.PersonNotFoundException;
import ru.maxima.restlibrary.models.Person;
import ru.maxima.restlibrary.security.PersonDetails;
import ru.maxima.restlibrary.service.BookService;
import ru.maxima.restlibrary.service.PersonService;
import ru.maxima.restlibrary.service.PersonServiceEncoder;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService service;


    private final ModelMapper modelMapper;

    private final PersonServiceEncoder encoder;


    @Autowired
    public PersonController(PersonService service, ModelMapper modelMapper, PersonServiceEncoder encoder) {
        this.service = service;
        this.modelMapper = modelMapper;
        this.encoder = encoder;
    }


    @PostMapping("/take")
    public ResponseEntity<HttpStatus> takeBook(@AuthenticationPrincipal UserDetails userDetails , BookDto bookDto) {
        service.takeBook(userDetails.getUsername(), bookDto);
        return ResponseEntity.ok(HttpStatus.OK);
//        Метод дает книгу персону по полям BookDto
    }
    @GetMapping("/myBook")
    public List<BookDto> myBook(@AuthenticationPrincipal UserDetails userDetails){
        return service.getBooks(userDetails);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/persons/{id}")
    public PersonDto getPerson(@PathVariable Long id ) throws PersonNotFoundException {
        return modelMapper.map(service.findById(id) , PersonDto.class);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/created")
    public ResponseEntity<HttpStatus> createPerson(@RequestBody @Valid PersonDto personDto  ,
                                                   @AuthenticationPrincipal UserDetails userDetails,
                                                   BindingResult bindingResult){

        service.checkErrors(bindingResult);

        Person person = modelMapper.map(personDto , Person.class);
        person.setPassword(encoder.savePassword(person.getPassword()));
        service.changesPerson(person , userDetails.getUsername());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deletePerson(@PathVariable("id") Long id ,
                                                   @AuthenticationPrincipal UserDetails userDetails){

        service.deletePerson(id, userDetails.getUsername());
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
