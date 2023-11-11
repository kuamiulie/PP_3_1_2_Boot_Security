package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UniqueNameAnnotation;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;


@Controller
@RequestMapping("/admin")
@Validated
public class AdminController {


    private final UserService userService;

    private RoleServiceImpl roleService;

    @Autowired
    public AdminController(UserService userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getAdminPage(Model model, Principal user) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", userService.findByUsername(user.getName()));
        model.addAttribute("roles", Set.of(new Role("ROLE_USER"), new Role("ROLE_ADMIN")));
        return "all-users";
    }

    @PostMapping("/save_user")
    @UniqueNameAnnotation(message = "NAME IS ALREADY USED")
    public String saveUser(@ModelAttribute(value = "user") @Valid User user, BindingResult bindingResult,
                           @RequestParam(value = "checkBoxRoles") String[] checkBoxRoles) {
        if (bindingResult.hasErrors()) {
            System.out.println("BINDING RESULT ERROR");
            return "all-users";
        }

        Set<Role> roleSet = new HashSet<>();
        for (String roles : checkBoxRoles) {
            roleSet.add(roleService.getRoleByName(roles));
        }
        user.setRoles(roleSet);
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete_user")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
