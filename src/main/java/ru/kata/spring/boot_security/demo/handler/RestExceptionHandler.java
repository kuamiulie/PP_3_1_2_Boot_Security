package ru.kata.spring.boot_security.demo.handler;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.kata.spring.boot_security.demo.util.AlreadyTakenUserNameException;
import ru.kata.spring.boot_security.demo.util.InvalidNameOrCountryException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public void handleEntityNotFoundException(EntityNotFoundException e, HttpServletResponse response) throws IOException {
        log.error(e.getMessage());
        response.sendError(404, e.getMessage());
    }

    @ExceptionHandler(AlreadyTakenUserNameException.class)
    public void handleEntityTakenUserNameException(Exception e, HttpServletResponse response) throws IOException {
        log.error(e.getMessage());
        response.sendError(400, e.getMessage());
    }

    @ExceptionHandler(InvalidNameOrCountryException.class)
    public void handleEntityInvalidNamException(Exception e, HttpServletResponse response) throws IOException {
        log.error(e.getMessage());
        response.sendError(400, e.getMessage());
    }
}
