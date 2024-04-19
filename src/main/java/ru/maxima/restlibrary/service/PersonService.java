package ru.maxima.restlibrary.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.maxima.restlibrary.dto.BookDto;
import ru.maxima.restlibrary.dto.PersonDto;
import ru.maxima.restlibrary.exceptions.PersonNotCreatedException;
import ru.maxima.restlibrary.exceptions.PersonNotFoundException;
import ru.maxima.restlibrary.models.Book;
import ru.maxima.restlibrary.models.Person;
import ru.maxima.restlibrary.repositories.BookRepository;
import ru.maxima.restlibrary.repositories.PersonRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository repository;

    private final BookRepository bookRepository;

    private final BookService service;


    private final ModelMapper modelMapper;

    @Autowired
    public PersonService(PersonRepository repository, BookRepository bookRepository, BookService service, ModelMapper modelMapper) {
        this.repository = repository;
        this.bookRepository = bookRepository;
        this.service = service;
        this.modelMapper = modelMapper;
    }

    public Person findById(Long id){
        Optional<Person> getId = repository.findById(id);
        return getId.orElseThrow(PersonNotFoundException :: new);
    }

    public void save(Person person){
        repository.save(person);
    }

    public List<BookDto> getBooks(UserDetails userDetails){
        Person person = repository.findByName(userDetails.getUsername());
        List<BookDto> bookDtos = new ArrayList<>();
        List<Book> books = person.getBooks();
        books.forEach(a -> bookDtos.add(modelMapper.map(a , BookDto.class)));
        return bookDtos;
    }

    public void takeBook(String name, BookDto bookDTO) {
        Book book = service.getBookName(bookDTO.getName());
        Person person = findByName(name);
        List<Book> books = person.getBooks();
        books.add(book);
        book.setOwner(person);
        person.setBooks(books);
        bookRepository.save(book);
    }
    public void deletePerson(Long id , String deleteOrName  ){
        Person byId = findById(id);
        byId.setRemovedPerson(deleteOrName);
        byId.setRemovedAt(LocalDateTime.now());
        save(byId);
    }

    public Person findByName(String name){
        return repository.findByName(name);
    }

    public void changesPerson(Person person , String creatorName) {
        person.setCreatedPerson(creatorName);
        person.setCreatedAt(LocalDateTime.now());
        save(person);
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
