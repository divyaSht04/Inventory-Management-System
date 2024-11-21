package com.individual.divya.inventorymanagementsystem.controller;

import com.individual.divya.inventorymanagementsystem.entity.Vendor;
import com.individual.divya.inventorymanagementsystem.service.VendorService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendor")
@Tag(name = "Vendor API", description = "")
@SecurityRequirement(name = "BearerAuth")
public class VendorController {

    @Autowired
    public VendorService vendorService;

    @GetMapping
    public ResponseEntity<?> getAllVendors(){
        return new ResponseEntity<>(vendorService.getAllVendors(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addVendor(@RequestBody Vendor vendor){
        vendorService.addVendor(vendor);
        return new ResponseEntity<>("Vendor Added Successfully", HttpStatus.CREATED);
    }

    @GetMapping("/vendorId/{vendorId}")
    public ResponseEntity<?> getVendorById(@PathVariable long vendorId){
        Vendor vendor = vendorService.getVendorByID(vendorId);
        return new ResponseEntity<>(vendor, HttpStatus.OK);
    }

    @DeleteMapping("/vendorId/{vendorId}")
    public ResponseEntity<?> deleteVendor(@PathVariable long vendorId){
        vendorService.deleteVendor(vendorId);
        return new ResponseEntity<>("Vendor Deleted Successfully", HttpStatus.OK);
    }

}
