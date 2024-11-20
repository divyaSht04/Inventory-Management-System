package com.individual.divya.inventorymanagementsystem.controller;

import com.individual.divya.inventorymanagementsystem.entity.Vendor;
import com.individual.divya.inventorymanagementsystem.service.VendorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VendorControllerTest {

    @InjectMocks
    private VendorController vendorController;

    @Mock
    private VendorService vendorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllVendors() {
        List<Vendor> vendors = new ArrayList<>();
        vendors.add(new Vendor());
        when(vendorService.getAllVendors()).thenReturn(vendors);

        assertNotNull(vendors);
        assertEquals(1, vendors.size());
    }

    @Test
    void addVendor() {
        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setName("Test Vendor");

        doNothing().when(vendorService).addVendor(any(Vendor.class));

        String message = (String) vendorController.addVendor(vendor).getBody();

        assertEquals("Vendor Added Successfully", message);
        verify(vendorService, times(1)).addVendor(vendor);
    }

    @Test
    void getVendorById() {
        Vendor vendor = new Vendor();
        when(vendorService.getVendorByID(anyLong())).thenReturn(vendor);

        ResponseEntity<?> result = vendorController.getVendorById(1L);

        assertNotNull(result);
        verify(vendorService, times(1)).getVendorByID(anyLong());
    }

    @Test
    void deleteVendor() {
        doNothing().when(vendorService).deleteVendor(anyLong());

        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setName("Test Vendor");

        String message = (String) vendorController.deleteVendor(vendor.getId()).getBody();

        assertEquals("Vendor Deleted Successfully", message);
        verify(vendorService, times(1)).deleteVendor(anyLong());

    }
}
