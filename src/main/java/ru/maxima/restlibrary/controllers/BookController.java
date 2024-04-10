package ru.maxima.restlibrary.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.maxima.restlibrary.dto.BookDto;
import ru.maxima.restlibrary.models.Book;
import ru.maxima.restlibrary.models.Person;
import ru.maxima.restlibrary.service.BookService;

@RestController
public class BookController {

    private final BookService bookService;

    private final ModelMapper modelMapper;

    @Autowired
    public BookController(BookService bookService, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> update (@PathVariable("id") Long id,
                                                   @AuthenticationPrincipal UserDetails userDetails,
                                                   BindingResult bindingResult){



        bookService.updatePerson(id , userDetails.getUsername());
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
