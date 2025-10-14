package com.tejasTanra.suaraRakyat.service;

import com.tejasTanra.suaraRakyat.model.Role;
import com.tejasTanra.suaraRakyat.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role createRole(String name, Set<String> permissions) {
        Role role = new Role(name);
        role.setPermissions(permissions);
        return roleRepository.save(role);
    }

    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }
}
