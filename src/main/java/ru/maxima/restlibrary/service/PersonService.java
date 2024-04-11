package ru.maxima.restlibrary.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.maxima.restlibrary.dto.PersonDto;
import ru.maxima.restlibrary.exceptions.PersonNotCreatedException;
import ru.maxima.restlibrary.exceptions.PersonNotFoundException;
import ru.maxima.restlibrary.models.Person;
import ru.maxima.restlibrary.repositories.PersonRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository repository;

    private final ModelMapper modelMapper;

    @Autowired
    public PersonService(PersonRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }



    public List<Person> getAllPerson(){
        return repository.findAll();
    }

    public Person findById(Long id){
        Optional<Person> getId = repository.findById(id);
        return getId.orElseThrow(PersonNotFoundException :: new);
    }

    public void save(Person person){
        repository.save(person);
    }

    public void updatePerson(Long id){
        Person person = findById(id);
        person.setName(person.getName());
        person.setAge(person.getAge());
        person.setEmail(person.getEmail());
        person.setPhoneNumber(person.getPhoneNumber());
        repository.save(person);
    }

    public void deletePerson(Long id , String deleteOrName ){
        Person byId = findById(id);
        byId.setRemovedPerson(deleteOrName);
        byId.setRemovedAt(LocalDateTime.now());
        save(byId);
    }

    public Person findByName(String name){
        return repository.findByName(name).orElseThrow(PersonNotFoundException :: new);
    }
    public PersonDto convertToPersonDTO(Person person){
        return modelMapper.map(person , PersonDto.class);
    }

//    public Person convertToPerson(PersonDto personDTO) {
//        Person person = modelMapper.map(personDTO , Person.class);
////        changesPerson(person);
//        return person;
//    }

    public void changesPerson(Person person , String creatorName) {
//        person.setCreatedAt(LocalDateTime.now());
//        person.setRemovedAt(LocalDateTime.now());
        person.setCreatedPerson(creatorName);
        save(person);
//        person.setRemovedPerson(person.getName());// уточнить насчет этой строки
    }

    public void checkErrors(BindingResult result) {
        if (result.hasErrors()) {

            StringBuilder builder = new StringBuilder();

            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(error -> {
                builder.append(error.getField());
                builder.append("-");
                builder.append(error.getDefaultMessage());
            });
            throw new PersonNotCreatedException(builder.toString());

        }
    }


}
