package com.individual.divya.inventorymanagementsystem.controller;

import com.individual.divya.inventorymanagementsystem.entity.Role;
import com.individual.divya.inventorymanagementsystem.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RoleControllerTest {

    @InjectMocks
    private RoleController roleController;

    @Mock
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateRole_WhenRoleExists() {
        when(roleService.findByName(anyString())).thenReturn(new Role());

        Role role = new Role();
        role.setName("Admin");
        assertNotNull(roleController.createRole(role));
    }

    @Test
    public void testCreateRole_WhenRoleDoesNotExist() {
        when(roleService.findByName(anyString())).thenReturn(null);
        doNothing().when(roleService).saveRole(any(Role.class));

        Role role = new Role();
        role.setName("User");
        assertNotNull(roleController.createRole(role));
    }

    @Test
    public void testGetAllRoles() {
        when(roleService.getAllRoles()).thenReturn(Collections.emptyList());

        assertNotNull(roleController.getAllRoles());
    }
}
