package ru.maxima.restlibrary.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandlerBook extends ResponseEntityExceptionHandler {

    @ExceptionHandler({BookNotFoundException.class})
    public ResponseEntity<Object> handleException(BookNotFoundException ex) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BookNotCreatedException.class})
    public ResponseEntity<Object> handleException(BookNotCreatedException ex) {
        return new ResponseEntity<>(ex.getMessage() , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BookNotUpdateException.class})
    public ResponseEntity<Object> handleException(BookNotUpdateException ex) {
        return new ResponseEntity<>(ex.getMessage() , HttpStatus.UPGRADE_REQUIRED);
    }

}
