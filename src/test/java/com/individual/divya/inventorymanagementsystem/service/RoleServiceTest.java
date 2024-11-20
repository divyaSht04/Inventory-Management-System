package com.individual.divya.inventorymanagementsystem.service;

import com.individual.divya.inventorymanagementsystem.entity.Role;
import com.individual.divya.inventorymanagementsystem.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRoles() {
        Role role1 = new Role();
        Role role2 = new Role();
        List<Role> expectedRoles = Arrays.asList(role1, role2);

        when(roleRepository.findAll()).thenReturn(expectedRoles);

        List<Role> actualRoles = roleService.getAllRoles();

        assertEquals(expectedRoles.size(), actualRoles.size());
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    void testFindByName() {
        // Arrange
        String roleName = "ADMIN";
        Role expectedRole = new Role();
        expectedRole.setName(roleName);

        when(roleRepository.findByName(roleName)).thenReturn(expectedRole);

        // Act
        Role foundRole = roleService.findByName(roleName);

        // Assert
        assertNotNull(foundRole);
        assertEquals(roleName, foundRole.getName());
        verify(roleRepository, times(1)).findByName(roleName);
    }

    @Test
    void testSaveRole() {
        // Arrange
        Role role = new Role();
        role.setName("USER");

        // Act
        roleService.saveRole(role);

        // Assert
        verify(roleRepository, times(1)).save(role);
    }
}