package com.wot.services;

import com.wot.entitites.Roles;
import com.wot.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Roles getRoleForUser(String role) {
        return roleRepository.findByName(role);
    }

    public void saveAllRoles(List<Roles> roles) {
        roleRepository.saveAll(roles);
    }

    public List<Roles> getAllRoles() {
        return roleRepository.findAll();
    }


}
