package com.individual.divya.inventorymanagementsystem.controller;


import com.individual.divya.inventorymanagementsystem.entity.Role;
import com.individual.divya.inventorymanagementsystem.entity.Users;
import com.individual.divya.inventorymanagementsystem.service.RoleService;
import com.individual.divya.inventorymanagementsystem.service.UserService;
import com.individual.divya.inventorymanagementsystem.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    public RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        List<Users> users = userService.getAllUsers();
        if (users.isEmpty()) return new ResponseEntity<>("No users found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Users users) {
        Users existingUser = userService.findByEmail(users.getEmail());
        if (existingUser != null) return new ResponseEntity<>("User with email already exists", HttpStatus.CONFLICT);

        Role role1 = roleService.findByName("Staff");
        if (role1 == null){
            throw new RuntimeException("Staff role not found");
        }

        Role role2 = roleService.findByName("Admin");
        if (role2 == null){
            throw new RuntimeException("Admin role not found");
        }
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.getRoles().add(role1);
        users.getRoles().add(role2);

        userService.saveUser(users);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users users) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(users.getEmail(), users.getPassword())
            );

            if (authentication.isAuthenticated()) {
                String token = jwtUtil.generateToken(users.getEmail());
                return new ResponseEntity<>(token, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("An error occurred " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{email}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateUser(@PathVariable String email, @RequestBody Users users) {

        Users existingUser = userService.getByEmail(email);

        if (existingUser != null) {
            existingUser.setEmail(users.getEmail());
            existingUser.setPassword(users.getPassword());
            userService.saveUser(existingUser);
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{email}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        Users user = userService.getByEmail(email);
        if (user!= null) return new ResponseEntity<>(user, HttpStatus.OK);
        else return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }
}
