package com.individual.divya.inventorymanagementsystem.service;

import com.individual.divya.inventorymanagementsystem.entity.Vendor;
import com.individual.divya.inventorymanagementsystem.repository.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VendorServiceTest {

    @Mock
    private VendorRepository vendorRepository;

    @InjectMocks
    private VendorService vendorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllVendors() {
        // Arrange
        Vendor vendor1 = new Vendor();
        Vendor vendor2 = new Vendor();
        List<Vendor> expectedVendors = Arrays.asList(vendor1, vendor2);

        when(vendorRepository.findAll()).thenReturn(expectedVendors);

        // Act
        List<Vendor> actualVendors = vendorService.getAllVendors();

        // Assert
        assertEquals(expectedVendors.size(), actualVendors.size());
        verify(vendorRepository, times(1)).findAll();
    }

    @Test
    void testAddVendor_Successful() {
        Vendor vendor = new Vendor();
        vendor.setName("Test Vendor");

        vendorService.addVendor(vendor);

        verify(vendorRepository, times(1)).save(vendor);
    }

    @Test
    void testGetVendorById_Successful() {
        Long vendorId = 1L;
        Vendor expectedVendor = new Vendor();
        expectedVendor.setId(vendorId);

        when(vendorRepository.findById(vendorId)).thenReturn(Optional.of(expectedVendor));

        Vendor foundVendor = vendorService.getVendorByID(vendorId);

        assertNotNull(foundVendor);
        assertEquals(expectedVendor, foundVendor);
        verify(vendorRepository, times(1)).findById(vendorId);
    }

    @Test
    void testDeleteVendor() {
        Long vendorId = 1L;
        Vendor vendor = new Vendor();
        vendor.setId(vendorId);

        when(vendorRepository.findById(vendorId)).thenReturn(Optional.of(vendor));

        vendorService.deleteVendor(vendorId);

        verify(vendorRepository, times(1)).deleteById(vendorId);
    }
}