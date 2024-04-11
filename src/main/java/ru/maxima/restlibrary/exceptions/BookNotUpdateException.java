package ru.maxima.restlibrary.exceptions;

public class BookNotUpdateException extends RuntimeException{
    public BookNotUpdateException(String message) {
        super(message);
    }
}
