package ru.maxima.restlibrary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maxima.restlibrary.models.Book;
import ru.maxima.restlibrary.models.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person , Long> {

    Optional<Person> findByName(String name);
}
