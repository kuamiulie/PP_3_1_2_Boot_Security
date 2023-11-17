package ru.kata.spring.boot_security.demo.configs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Component
public class DBInit {

    private UserService userService;


    @Autowired
    public DBInit(UserServiceImpl userService) {
        this.userService = userService;
    }
    @Transactional
    @PostConstruct
    public void run() {


        Set<Role> adminRole = new HashSet<>();
        Set<Role> userRole = new HashSet<>();
        adminRole.add(new Role("ROLE_ADMIN"));
        userRole.add(new Role("ROLE_USER"));


        userService.addUser(new User("Amir", "Russia", "1234", adminRole));
        userService.addUser(new User("Andrew", "Russia", "12345", userRole));
    }
}

