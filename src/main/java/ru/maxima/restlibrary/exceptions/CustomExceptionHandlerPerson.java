package ru.maxima.restlibrary.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandlerPerson extends ResponseEntityExceptionHandler {
    @ExceptionHandler({PersonNotCreatedException.class})
    public ResponseEntity<Object> handleException(PersonNotCreatedException ex) {
        return new ResponseEntity<>(ex.getMessage() , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({PersonNotFoundException.class})
    public ResponseEntity<Object> handleException(PersonNotFoundException ex) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
