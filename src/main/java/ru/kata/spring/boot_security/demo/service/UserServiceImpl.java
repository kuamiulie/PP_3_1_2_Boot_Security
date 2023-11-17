package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.util.AlreadyTakenUserNameException;

import javax.persistence.EntityNotFoundException;


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

        User userEntity = repository.findByUsername(user.getName());
        if (userEntity != null) {
            if (!userEntity.getName().equals(user.getName()) | userEntity.getId() != user.getId()) {
                throw new AlreadyTakenUserNameException("THIS USERNAME IS ALREADY USED");
            }
        }

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
            repository.save(user);
            roleRepository.saveAll(user.getRoles());

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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User userEntity = repository.findByUsername(name);
        if (userEntity == null) {
            throw new EntityNotFoundException("NO SUCH USER");
        }
        return userEntity;
    }
}
