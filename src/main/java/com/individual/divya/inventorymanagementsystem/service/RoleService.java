package com.individual.divya.inventorymanagementsystem.service;

import com.individual.divya.inventorymanagementsystem.entity.Role;
import com.individual.divya.inventorymanagementsystem.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleService {

    @Autowired
    public RoleRepository roleRepository;

    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

    public Role findByName(String name){
        return roleRepository.findByName(name);
    }

    public void saveRole(Role role){
        roleRepository.save(role);
    }


}
