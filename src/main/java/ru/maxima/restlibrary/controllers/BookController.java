package ru.maxima.restlibrary.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maxima.restlibrary.dto.BookDto;
import ru.maxima.restlibrary.models.Book;
import ru.maxima.restlibrary.models.Person;
import ru.maxima.restlibrary.service.BookService;

import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;

    private final ModelMapper modelMapper;

    @Autowired
    public BookController(BookService bookService, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/book")
    public List<Book> allBook(){
        return bookService.getAllBook();
    }


    @PostMapping("/update/{id}")
    public ResponseEntity<HttpStatus> update (@PathVariable("id") Long id,
                                                   @AuthenticationPrincipal UserDetails userDetails,
                                                   BindingResult bindingResult){

        bookService.checkErrorsUpdate(bindingResult);


        bookService.updatePerson(id , userDetails.getUsername());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> created(@RequestBody BookDto bookDto ,
                                              @AuthenticationPrincipal UserDetails userDetails ,
                                              BindingResult bindingResult){
        bookService.checkErrorsCreated(bindingResult);

        Book book = modelMapper.map(bookDto , Book.class);
        bookService.createdPerson(book , userDetails.getUsername());
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PostMapping("/delete /{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id,
                                             @AuthenticationPrincipal UserDetails userDetails){

        bookService.deletePerson(id , userDetails.getUsername());
        return ResponseEntity.ok(HttpStatus.NOT_FOUND);
    }



}
