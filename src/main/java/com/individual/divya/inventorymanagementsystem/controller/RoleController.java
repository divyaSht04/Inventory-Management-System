package com.individual.divya.inventorymanagementsystem.controller;


import com.individual.divya.inventorymanagementsystem.entity.Role;
import com.individual.divya.inventorymanagementsystem.service.RoleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("role")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "ROLE API", description = "The Role Requests section of the Inventory Management System (IMS) API focuses on managing user roles within the system. These endpoints allow for the creation of new roles and retrieval of role details by ID. This functionality ensures that user permissions are appropriately defined and managed.")
public class RoleController {

    @Autowired
    private RoleService roleService;


    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody Role role){
        if(roleService.findByName(role.getName())!=null){
            return new ResponseEntity<>("Role already exists", HttpStatus.CONFLICT);
        }else{
            roleService.saveRole(role);
            return new ResponseEntity<>("Role created successfully", HttpStatus.CREATED);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllRoles(){
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }
}