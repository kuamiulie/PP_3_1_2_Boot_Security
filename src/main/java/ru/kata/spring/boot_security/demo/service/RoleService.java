package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

public interface RoleService {

    void addRole(Role role);

    Role getRoleById(Long id);
}
