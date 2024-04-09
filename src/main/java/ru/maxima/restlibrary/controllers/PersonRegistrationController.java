package ru.maxima.restlibrary.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maxima.restlibrary.dto.LoginDto;
import ru.maxima.restlibrary.dto.PersonDto;
import ru.maxima.restlibrary.jwt.JWTUtils;
import ru.maxima.restlibrary.models.Person;
import ru.maxima.restlibrary.security.PersonDetails;
import ru.maxima.restlibrary.service.PersonService;
import ru.maxima.restlibrary.validation.PersonValidation;

import java.util.Map;

@RestController
@RequestMapping("/a")
public class PersonRegistrationController {

    private final PersonService personService;
    private final PersonValidation validation;

    private final JWTUtils utils;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public PersonRegistrationController(PersonService personService, PersonValidation validation, JWTUtils utils, AuthenticationManager authenticationManager) {
        this.personService = personService;
        this.validation = validation;
        this.utils = utils;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/registration")
    public Map<String , String> personRegistration(@RequestBody @Valid PersonDto personDto ,
                                                   BindingResult bindingResult){
        Person person = personService.convertToPerson(personDto);

        validation.validate(person , bindingResult);

        if (bindingResult.hasErrors()){
            return Map.of("message" , "500");
        }

        personService.save(person);



        String token = utils.generateToken(person.getName());

        return Map.of("jwt - token" , token);
    }

    @GetMapping("/show")
    public Map<String , String> userInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        return Map.of("name" , personDetails.getUsername());
    }


    @PostMapping("/login")
    public Map<String , Object> login(@RequestBody LoginDto loginDto){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getName() , loginDto.getPassword());

        try {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (AuthenticationException e) {
            return Map.of("message" , "500");
        }
        Person personToken = personService.findByName(loginDto.getName());

        String token = utils.generateToken(loginDto.getName());

        return Map.of("jwt - token", token, "name", personToken.getName(), "age", personToken.getAge(),
                "email", personToken.getEmail(), "phoneNumber", personToken.getPhoneNumber(),
                "role", personToken.getRole());
    }



}