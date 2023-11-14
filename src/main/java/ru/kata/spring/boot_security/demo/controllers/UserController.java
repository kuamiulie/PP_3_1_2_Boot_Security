package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping()
public class UserController {

    @GetMapping("/users")
    public String showAll() {
        return "all-users";
    }

    @GetMapping("/user")
    public String showUser() {
        return "user";
    }

}