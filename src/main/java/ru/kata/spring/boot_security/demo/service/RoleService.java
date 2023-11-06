package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

@Service
@Transactional
public class RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional

    public void addRole(Role role) {
        roleRepository.save(role);
    }

    @Transactional

    public void updateRole(Role role) {
        roleRepository.saveAndFlush(role);
    }

    @Transactional

    public void removeRoleById(long id) { roleRepository.deleteById((long) id); }

    @Transactional
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Transactional
    public Role getRoleByName(String username) {
        return roleRepository.findByRoleName(username);
    }

    @Transactional
    public Role getRoleById(Long id) {
        return roleRepository.getById(id);
    }
}
