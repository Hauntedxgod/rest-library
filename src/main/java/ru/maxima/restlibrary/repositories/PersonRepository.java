package ru.maxima.restlibrary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maxima.restlibrary.models.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person , Long> {

    Person findByName(String name);
}
