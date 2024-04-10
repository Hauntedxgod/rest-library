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
import ru.maxima.restlibrary.dto.PersonDto;
import ru.maxima.restlibrary.models.Person;
import ru.maxima.restlibrary.service.PersonService;

@RestController
@RequestMapping()
public class PersonController {

    private final PersonService service;


    private final ModelMapper modelMapper;


    @Autowired
    public PersonController(PersonService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/{id}")
    public PersonDto getPerson(@PathVariable Long id){
        return service.convertToPersonDTO(service.findById(id));
    }


    @PostMapping()
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



}
