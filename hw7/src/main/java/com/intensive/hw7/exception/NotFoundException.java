package com.intensive.hw7.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("Requested data is not present");

    }

}