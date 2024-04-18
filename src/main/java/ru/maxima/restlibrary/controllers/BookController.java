package ru.maxima.restlibrary.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maxima.restlibrary.dto.BookDto;
import ru.maxima.restlibrary.dto.PersonDto;
import ru.maxima.restlibrary.exceptions.BookNotFoundException;
import ru.maxima.restlibrary.models.Book;
import ru.maxima.restlibrary.service.BookService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    private final ModelMapper modelMapper;

    @Autowired
    public BookController(BookService bookService, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }




    @GetMapping("/books")
    public List<BookDto> allBook(){
        return bookService.getAllBook();
    }


    @GetMapping("/{id}")
    public BookDto showBookById(@PathVariable Long id) throws BookNotFoundException{
       return modelMapper.map(bookService.findById(id) , BookDto.class );
    }

    @GetMapping("/getBook/{id}")
    public PersonDto getBookOwner(@PathVariable("id") Long id) {
        Book book = bookService.findById(id);
        return modelMapper.map(book.getOwner(), PersonDto.class);
        // Метод показывает какая книга по Id у Person
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update/{id}")
    public ResponseEntity<HttpStatus> update (@PathVariable("id") Long id , BookDto bookDto,
                                                   @AuthenticationPrincipal UserDetails userDetails,
                                                   BindingResult bindingResult){

        bookService.checkErrorsUpdate(bindingResult);


        bookService.updatePerson(id , bookDto , userDetails.getUsername());
        return ResponseEntity.ok(HttpStatus.OK);
        // метод производит дополнение книги
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/created")
    public ResponseEntity<HttpStatus> created(@RequestBody BookDto bookDto ,
                                              @AuthenticationPrincipal UserDetails userDetails ,
                                              BindingResult bindingResult){
        bookService.checkErrorsCreated(bindingResult);

        Book book = modelMapper.map(bookDto , Book.class);
        bookService.createdPerson(book , userDetails.getUsername());
        return ResponseEntity.ok(HttpStatus.CREATED);
        // метод производит редактирование книги
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete /{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id,
                                             @AuthenticationPrincipal UserDetails userDetails){

        bookService.deletePerson(id , userDetails.getUsername());
        return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        // метод производит удаление книги
    }
}
