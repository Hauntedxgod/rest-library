package ru.maxima.restlibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.maxima.restlibrary.models.Person;
import ru.maxima.restlibrary.repositories.PersonRepository;

@Service
public class PersonServiceEncoder {

    private final PersonRepository personRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonServiceEncoder(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void savePassword(Person person){
        String password = passwordEncoder.encode(person.getPassword());
        person.setPassword(password);
        personRepository.save(person);
    }
}
