package ru.maxima.restlibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.maxima.restlibrary.models.Person;
import ru.maxima.restlibrary.security.PersonDetails;

@Service
public class PersonDetailsService implements UserDetailsService {

    private final PersonService personService;

    @Autowired
    public PersonDetailsService(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person personName = personService.findByName(username);

        if (personName == null) {
            throw new UsernameNotFoundException("Not found user ");
        }
        return new PersonDetails(personName);
    }
}
