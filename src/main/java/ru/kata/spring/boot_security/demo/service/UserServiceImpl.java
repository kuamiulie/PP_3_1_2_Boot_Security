package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private PasswordEncoder passwordEncoder;

    private UserRepository repository;

    private RoleRepository roleRepository;

    public UserServiceImpl() {
    }

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }



    @Override
    @Transactional
    public void addUser(User user) {

        Long id;
        Optional<String> roleName;
        Set<Role> roles = new HashSet<>();

        for (Role role: user.getRoles()) {
            id = role.getId();
            roleName = Optional.ofNullable(role.getName());

            if (roleName.isEmpty()) {
                roles.add(roleRepository.findRoleById(id));
                user.setRoles(roles);
            } else {
                user.setRoles(user.getRoles());
            }
        }

        if (user.getId() == 0) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            repository.save(user);
            roleRepository.saveAll(user.getRoles());
        } else {
            roleRepository.saveAll(user.getRoles());
            user.setRoles(user.getRoles());
            repository.save(user);
        }
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUsers() {
        return  repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }
}
