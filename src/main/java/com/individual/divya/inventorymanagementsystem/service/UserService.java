package com.individual.divya.inventorymanagementsystem.service;

import com.individual.divya.inventorymanagementsystem.entity.Users;
import com.individual.divya.inventorymanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;


    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Users findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUser(Users users) {
        userRepository.save(users);
    }

    public Users getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void deleteUser(Users existingUser) {
        userRepository.delete(existingUser);
    }
}
