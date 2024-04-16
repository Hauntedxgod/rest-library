package ru.maxima.restlibrary.validation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.maxima.restlibrary.exceptions.PersonNotFoundException;
import ru.maxima.restlibrary.models.Person;
import ru.maxima.restlibrary.service.PersonDetailsService;

@Component
public class PersonValidation implements Validator {


    private final PersonDetailsService service;

    @Autowired
    public PersonValidation(PersonDetailsService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person p = (Person) target;

        try {
            service.loadUserByUsername(p.getName());
        } catch (PersonNotFoundException e) {
            return;
        }
        errors.rejectValue("name" , "100" , "User with this nickName existed");
    }
}
