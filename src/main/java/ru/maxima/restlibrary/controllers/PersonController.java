package ru.maxima.restlibrary.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maxima.restlibrary.dto.PersonDto;
import ru.maxima.restlibrary.models.Person;
import ru.maxima.restlibrary.service.PersonService;

@RestController
@RequestMapping()
public class PersonController {

    private final PersonService service;


    @Autowired
    public PersonController(PersonService service) {
        this.service = service;
    }


    @GetMapping("/{id}")
    public PersonDto getPerson(@PathVariable Long id){
        return service.convertToPersonDTO(service.findById(id));
    }


    @PostMapping()
    public ResponseEntity<HttpStatus> createPerson(@RequestBody @Valid PersonDto personDto  , BindingResult bindingResult){

        service.checkErrors(bindingResult);

        Person person = service.convertToPerson(personDto);
        service.save(person);
        return ResponseEntity.ok(HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePerson(@PathVariable("id") Long id , PersonDto personDto){

        Person person = service.convertToPerson(personDto);
        service.save(person);
        service.deletePerson(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }



}
