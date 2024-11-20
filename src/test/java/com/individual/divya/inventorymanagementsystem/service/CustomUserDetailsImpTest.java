package com.individual.divya.inventorymanagementsystem.service;

import com.individual.divya.inventorymanagementsystem.entity.Role;
import com.individual.divya.inventorymanagementsystem.entity.Users;
import com.individual.divya.inventorymanagementsystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CustomUserDetailsImpTest {

    @InjectMocks
    private CustomUserDetailsImp CustomUserDetailsImpTest;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUserByUsername() {

        Users user = new Users();
        user.setEmail("test");
        user.setPassword("test");

        Role role = new Role();
        role.setName("USER");

        user.getRoles().add(role);

        when(userRepository.findByEmail(ArgumentMatchers.anyString())).thenReturn(user);
        UserDetails userDetails= CustomUserDetailsImpTest.loadUserByUsername("test");
        assertNotNull(userDetails);
    }
}