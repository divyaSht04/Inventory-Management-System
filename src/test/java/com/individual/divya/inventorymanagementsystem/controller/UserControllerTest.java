package com.individual.divya.inventorymanagementsystem.controller;

import com.individual.divya.inventorymanagementsystem.entity.Role;
import com.individual.divya.inventorymanagementsystem.entity.Users;
import com.individual.divya.inventorymanagementsystem.service.RoleService;
import com.individual.divya.inventorymanagementsystem.service.UserService;
import com.individual.divya.inventorymanagementsystem.util.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTUtil jwtUtil;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllUsers() {
        // Arrange
        List<Users> expectedUsers = new ArrayList<>();
        Users user = new Users();
        user.setEmail("test@test.com");
        expectedUsers.add(user);

        when(userService.getAllUsers()).thenReturn(expectedUsers);

        List<Users> actualUsers = (List<Users>) userController.getAllUsers().getBody();

        assertNotNull(actualUsers);
        assertEquals(expectedUsers.size(), actualUsers.size());
        assertEquals(expectedUsers.get(0).getEmail(), actualUsers.get(0).getEmail());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void register() {
        // Arrange
        Users user = new Users();
        user.setEmail("test@test.com");
        user.setPassword("password");
        user.setRoles(new ArrayList<>());

        Role staffRole = new Role();
        staffRole.setName("Staff");
        Role adminRole = new Role();
        adminRole.setName("Admin");

        when(userService.findByEmail(any())).thenReturn(null);
        when(roleService.findByName("Staff")).thenReturn(staffRole);
        when(roleService.findByName("Admin")).thenReturn(adminRole);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        String registrationMessage = (String) userController.register(user).getBody();

        assertEquals("User registered successfully", registrationMessage);
        verify(userService, times(1)).saveUser(any(Users.class));
        verify(passwordEncoder, times(1)).encode("password");
        verify(roleService, times(1)).findByName("Staff");
        verify(roleService, times(1)).findByName("Admin");
    }

    @Test
    void login() {
        Users user = new Users();
        user.setEmail("test@test.com");
        user.setPassword("password");
        String expectedToken = "test-jwt-token";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(jwtUtil.generateToken(any())).thenReturn(expectedToken);

        // Act
        String actualToken = (String) userController.login(user).getBody();

        // Assert
        assertEquals(expectedToken, actualToken);
        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil, times(1)).generateToken(user.getEmail());
    }

    @Test
    void updateUser() {
        // Arrange
        String email = "test@test.com";
        Users updatedUser = new Users();
        updatedUser.setEmail("newtest@test.com");
        updatedUser.setPassword("newpassword");

        Users existingUser = new Users();
        existingUser.setEmail(email);

        when(userService.getByEmail(email)).thenReturn(existingUser);

        // Act
        String updateMessage = (String) userController.updateUser(email, updatedUser).getBody();

        // Assert
        assertEquals("User updated successfully", updateMessage);
        verify(userService, times(1)).getByEmail(email);
        verify(userService, times(1)).saveUser(existingUser);
    }

    @Test
    void getUserByEmail() {
        // Arrange
        String email = "test@test.com";
        Users expectedUser = new Users();
        expectedUser.setEmail(email);

        when(userService.getByEmail(email)).thenReturn(expectedUser);

        // Act
        Users actualUser = (Users) userController.getUserByEmail(email).getBody();

        // Assert
        assertNotNull(actualUser);
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        verify(userService, times(1)).getByEmail(email);
    }
}