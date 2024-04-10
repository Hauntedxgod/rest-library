package ru.maxima.restlibrary.service;

import org.springframework.stereotype.Service;
import ru.maxima.restlibrary.exceptions.BookNotFoundException;
import ru.maxima.restlibrary.exceptions.PersonNotFoundException;
import ru.maxima.restlibrary.models.Book;
import ru.maxima.restlibrary.models.Person;
import ru.maxima.restlibrary.repositories.BookRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public Book findById(Long id){
        Optional<Book> getId = bookRepository.findById(id);
        return getId.orElseThrow(BookNotFoundException:: new);
    }

    public void save(Book book){
        bookRepository.save(book);
    }

    public void updatePerson(Long id , String update){
        Book byId = findById(id);
        byId.setName(byId.getName());
        byId.setYearOfProduction(byId.getYearOfProduction());
        byId.setAuthor(byId.getAuthor());
        byId.setAnnotation(byId.getAnnotation());
        byId.setUpdatedPerson(update);
        save(byId);
    }

    public void createdPerson(Book book , String created){
        book.setCreatedPerson(created);
        save(book);
    }

    public void deletePerson(Long id , String delete){
        Book byId = findById(id);
        byId.setRemovedPerson(delete);
        byId.setRemovedAt(LocalDateTime.now());
        save(byId);
    }




}
