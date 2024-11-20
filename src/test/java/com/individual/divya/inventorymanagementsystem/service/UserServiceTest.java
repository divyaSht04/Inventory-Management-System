package com.individual.divya.inventorymanagementsystem.service;

import com.individual.divya.inventorymanagementsystem.entity.Users;
import com.individual.divya.inventorymanagementsystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        Users user1 = new Users();
        Users user2 = new Users();
        List<Users> expectedUsers = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(expectedUsers);

        // Act
        List<Users> actualUsers = userService.getAllUsers();

        // Assert
        assertEquals(expectedUsers.size(), actualUsers.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindByEmail() {
        // Arrange
        String email = "test@example.com";
        Users expectedUser = new Users();
        expectedUser.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(expectedUser);

        // Act
        Users foundUser = userService.findByEmail(email);

        // Assert
        assertNotNull(foundUser);
        assertEquals(email, foundUser.getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testSaveUser() {
        // Arrange
        Users user = new Users();
        user.setEmail("test@example.com");

        // Act
        userService.saveUser(user);

        // Assert
        verify(userRepository, times(1)).save(user);
    }
}