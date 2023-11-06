package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAdminPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "all-users";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
            model.addAttribute("user", new User());
            return "user-create";
    }

    @PostMapping
    public String saveUser(@ModelAttribute(value = "user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("BINDING RESULT ERROR");
            return "user-create";
        }
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/update_user")
    public String updateUser(@RequestParam("id") int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user-create";
    }

    @PostMapping("/delete_user")
    public String deleteUser(@RequestParam("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
