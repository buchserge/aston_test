package ru.intensive.customspringbootstarter;

public class IllegalFirstLetterException extends RuntimeException{
    public IllegalFirstLetterException() {
        super("String must start with a capital letter");

    }
}
