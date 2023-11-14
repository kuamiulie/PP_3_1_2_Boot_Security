package ru.kata.spring.boot_security.demo.util;

public class AlreadyTakenUserNameException extends RuntimeException {
    public AlreadyTakenUserNameException(String message) {
        super(message);
    }
}

