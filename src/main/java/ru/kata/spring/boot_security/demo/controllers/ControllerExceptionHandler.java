package ru.kata.spring.boot_security.demo.controllers;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest req, Exception exception) {
        log.error(exception.getMessage());
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", exception.getMessage());
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error500");
        return mav;
    }
}
