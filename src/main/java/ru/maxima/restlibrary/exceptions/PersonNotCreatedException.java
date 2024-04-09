package ru.maxima.restlibrary.exceptions;

public class PersonNotCreatedException extends RuntimeException{

    public PersonNotCreatedException(String message) {
        super(message);
    }
}
