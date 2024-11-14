package com.individual.divya.inventorymanagementsystem.service;

import com.individual.divya.inventorymanagementsystem.config.CustomUserDetails;
import com.individual.divya.inventorymanagementsystem.entity.Users;
import com.individual.divya.inventorymanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsImp implements UserDetailsService {

    @Autowired
    public UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Users users = userRepository.findByEmail(email);

        if(users == null){
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return new CustomUserDetails(users);
    }
}
