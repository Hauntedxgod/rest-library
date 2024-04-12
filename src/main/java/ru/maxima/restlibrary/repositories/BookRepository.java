package ru.maxima.restlibrary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maxima.restlibrary.models.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book , Long> {

    List<Book> findByOwner_Id(Long id);
}
