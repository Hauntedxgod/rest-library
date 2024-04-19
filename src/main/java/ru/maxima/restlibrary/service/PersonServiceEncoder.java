package ru.maxima.restlibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.maxima.restlibrary.models.Person;
import ru.maxima.restlibrary.repositories.PersonRepository;

import java.time.LocalDateTime;

@Service
public class PersonServiceEncoder {



    private final PersonRepository personRepository;


    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonServiceEncoder(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String savePassword(String input){
        return passwordEncoder.encode(input);
    }

    public void savePerson(Person person){
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_ADMIN");
        person.setCreatedAt(LocalDateTime.now());
        person.setCreatedPerson(person.getName());
        personRepository.save(person);
    }
}
