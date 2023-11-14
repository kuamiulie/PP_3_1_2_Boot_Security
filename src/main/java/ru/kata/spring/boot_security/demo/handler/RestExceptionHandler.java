package ru.kata.spring.boot_security.demo.handler;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<HttpStatus> NotFoundExceptionHandler(EntityNotFoundException e, HttpServletResponse response) throws IOException {
        log.error(e.getMessage());
        response.sendError(204, e.getMessage());
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyTakenUserNameException.class)
    public ResponseEntity<HttpStatus> TakenUserNameExceptionHandler(Exception e, HttpServletResponse response) throws IOException {
        log.error(e.getMessage());
        response.sendError(400, e.getMessage());
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidNameOrCountryException.class)
    public ResponseEntity<HttpStatus> InvalidNamExceptionHandler(Exception e, HttpServletResponse response) throws IOException {
        log.error(e.getMessage());
        response.sendError(400, e.getMessage());
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
