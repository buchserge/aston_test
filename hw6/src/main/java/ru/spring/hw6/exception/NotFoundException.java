package ru.spring.hw6.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("Requested data is not present");

    }

}