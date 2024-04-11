package ru.maxima.restlibrary.exceptions;

public class BookNotCreatedException extends RuntimeException{
    public BookNotCreatedException(String message) {
        super(message);
    }
}
