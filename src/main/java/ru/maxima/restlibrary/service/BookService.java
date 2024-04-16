package ru.maxima.restlibrary.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.maxima.restlibrary.dto.BookDto;
import ru.maxima.restlibrary.exceptions.*;
import ru.maxima.restlibrary.models.Book;
import ru.maxima.restlibrary.repositories.BookRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    private final ModelMapper modelMapper;

    public BookService(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
//        this.bookDTORepository = bookDTORepository;
        this.modelMapper = modelMapper;
    }

    public List<Book> getOwnerId(Long ownerId){
        return bookRepository.findByOwner_Id(ownerId);
    }


    @PreAuthorize("hasRole('ADMIN')")
    public List<BookDto> getAllBook(){
        List<BookDto> bookDtos = new ArrayList<>();
        List<Book> allBook = bookRepository.findAll();
        for (int i = 0; i < allBook.size() ; i++) {
            bookDtos.add(modelMapper.map(allBook.get(i) , BookDto.class));
        }
        return bookDtos;
    }


    public Book findById(Long id){
        Optional<Book> getId = bookRepository.findById(id);
        return getId.orElseThrow(BookNotFoundException:: new);
    }

    public Book getBookName(String name){
        return bookRepository.findByName(name).orElseThrow(BookNotFoundException :: new);
    }

    public void save(Book book){
        bookRepository.save(book);
    }

    public void updatePerson(Long id , BookDto bookDto , String update){
        Book byId = findById(id);
        byId.setName(bookDto.getName());
        byId.setYearOfProduction(bookDto.getYearOfProduction());
        byId.setAuthor(bookDto.getAuthor());
        byId.setAnnotation(bookDto.getAnnotation());
        byId.setUpdatedPerson(update);
        byId.setUpdatedAt(LocalDateTime.now());
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

    public void checkErrorsUpdate(BindingResult result) {
        if (result.hasErrors()) {

            StringBuilder builder = new StringBuilder();

            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(error -> {
                builder.append(error.getField());
                builder.append("-");
                builder.append(error.getDefaultMessage());
            });
            throw new BookNotUpdateException(builder.toString());

        }
    }
    public void checkErrorsCreated(BindingResult result) {
        if (result.hasErrors()) {

            StringBuilder builder = new StringBuilder();

            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(error -> {
                builder.append(error.getField());
                builder.append("-");
                builder.append(error.getDefaultMessage());
            });
            throw new BookNotCreatedException(builder.toString());

        }
    }



}
