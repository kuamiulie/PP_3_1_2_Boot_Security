package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.AlreadyTakenUserNameException;
import ru.kata.spring.boot_security.demo.util.InvalidNameOrCountryException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
@Validated
@CrossOrigin
public class RestController {

    private final UserService userService;

    private final UserRepository repository;

    @Autowired
    public RestController(UserService userService, UserRepository repository) {
        this.userService = userService;
        this.repository = repository;
    }

    @GetMapping("/admin/show_users")
    public ResponseEntity<List<User>> getAdminPage() {
        List<User> userList = userService.getAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/authority")
    public ResponseEntity<User> getRoles(Principal user) {
        User userEntity = userService.findByUsername(user.getName());
        if (userEntity == null) {
            throw new EntityNotFoundException("NO SUCH USER");
        }
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }

    @PostMapping("/admin/save_user")
    public User saveUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidNameOrCountryException("USER'S OR COUNTRY'S NAME IS INVALID");
        }

        User userEntity = repository.findByUsername(user.getName());
        if (userEntity != null) {
            if (!userEntity.getName().equals(user.getName()) | userEntity.getId() != user.getId()) {
                throw new AlreadyTakenUserNameException("THIS USERNAME IS ALREADY USED");
            }
        }

        userService.addUser(user);
        return user;
    }

    @DeleteMapping(value = "/admin/delete_user")
    public ResponseEntity<User>  deleteUser(@RequestBody User user) {
        User userEntity = userService.findByUsername(user.getName());
        userEntity.setRoles(user.getRoles());
        userService.deleteUser(userEntity.getId());
        return new ResponseEntity<>(userEntity, HttpStatus.NO_CONTENT);
    }
}
