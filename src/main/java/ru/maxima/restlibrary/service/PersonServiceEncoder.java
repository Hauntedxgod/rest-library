package ru.maxima.restlibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.maxima.restlibrary.models.Person;
import ru.maxima.restlibrary.repositories.PersonRepository;

@Service
public class PersonServiceEncoder {



    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonServiceEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String savePassword(String input){
        return passwordEncoder.encode(input);
    }
}
