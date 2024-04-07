package ru.maxima.restlibrary.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maxima.restlibrary.models.Person;
import ru.maxima.restlibrary.repositories.PersonRepository;

@Service
public class PersonService {

    private final PersonRepository repository;

    private final ModelMapper modelMapper;

    @Autowired
    public PersonService(PersonRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }


    public Person findByName(String name){
        return repository.findByName(name);
    }
}
