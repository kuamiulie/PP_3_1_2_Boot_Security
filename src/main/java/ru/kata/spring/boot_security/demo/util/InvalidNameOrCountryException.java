package ru.kata.spring.boot_security.demo.util;

public class InvalidNameOrCountryException extends RuntimeException {
    public InvalidNameOrCountryException(String message) {
        super(message);
    }
}
